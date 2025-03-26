package org.solar.system.mdm.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.solar.system.central.common.all.annotations.HistorizeDataPerform;
import org.solar.system.central.common.all.enums.ActionSystemEnum;
import org.solar.system.central.common.all.exceptions.BusinessException;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.solar.system.central.common.mdm.dto.ModuleInfoDto;
import org.solar.system.central.common.mdm.dto.ModuleRefDto;
import org.solar.system.central.common.mdm.dto.ParamVersionListDto;
import org.solar.system.central.common.mdm.dto.VersionListDto;
import org.solar.system.central.common.mdm.request.EndpointRefRequest;
import org.solar.system.central.common.mdm.request.ModuleInfoRequest;
import org.solar.system.central.common.mdm.request.VersionRefRequest;
import org.solar.system.mdm.common.enums.EndpointLocking;
import org.solar.system.mdm.common.exception.EndpointUrlDuplicateException;
import org.solar.system.mdm.common.exception.ModuleCodeDuplicateException;
import org.solar.system.mdm.common.exception.VersionNumberDuplicateException;
import org.solar.system.mdm.common.utils.BundleConstants;
import org.solar.system.mdm.model.entities.pmversion.EndpointRef;
import org.solar.system.mdm.model.entities.pmversion.ModuleRef;
import org.solar.system.mdm.model.entities.pmversion.MonitoringParamVersion;
import org.solar.system.mdm.model.entities.pmversion.VersionRef;
import org.solar.system.mdm.model.repository.pmversion.EndpointRefRepository;
import org.solar.system.mdm.model.repository.pmversion.ModuleRefRepository;
import org.solar.system.mdm.model.repository.pmversion.MonitoringParamVersionRepository;
import org.solar.system.mdm.model.repository.pmversion.VersionRefRepository;
import org.solar.system.mdm.model.request.ParamVersionRequest;
import org.solar.system.mdm.service.api.KafkaProducerService;
import org.solar.system.mdm.service.api.MonitoringParamVersionService;
import org.solar.system.mdm.service.base.AbstractService;
import org.solar.system.mdm.service.config.TranslatorProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MonitoringParamVersionServiceImpl extends AbstractService implements MonitoringParamVersionService {

	private final ModuleRefRepository moduleRefRepository;
	private final EndpointRefRepository endpointRefRepository;
	private final VersionRefRepository versionRefRepository;
	private final MonitoringParamVersionRepository apiVersionParamRepository;

	/** Kafka service */
	private final KafkaProducerService producer;

	@Override
	public ModuleRef getOrCreateModuleRef(ModuleInfoRequest dto, boolean useCache) {

		String moduleCode = dto.getCode();
		final ModuleRef moduleEntity = getModuleByIdentifier(Optional.of(moduleCode), Boolean.FALSE, useCache);
		if (moduleEntity == null) {
			return createSingleEntityModule(dto);
		}

		/* update label if present (not empty) */
		Optional.ofNullable(dto.getLabel())
				.filter(StringUtils::isNotBlank)
				.map(StringUtils::trimToNull)
				.filter(StringUtils::isNotEmpty)
				.filter(l -> !StringUtils.equalsIgnoreCase(moduleEntity.getLabel(), l))
				.ifPresent(moduleEntity::setLabel);

		return moduleEntity;

	}

	@Override
	public void quickNewParamModule(ParamVersionRequest data, boolean useCache) throws BusinessException {

		log.info("Create new param api version : {}", writeObjectAsString(data, Boolean.TRUE));

		/* Get or Create Module (update if label changed !!) */
		ModuleRef moduleEntity = getOrCreateModuleRef(data.getModule(), useCache);

		/* Create Endpoints */
		createEndpoints(moduleEntity, data.getEndpoints());

		/* Create Version */
		createVersions(moduleEntity, data.getVersions());

		/* Flushing pending module */
		moduleRefRepository.flush();

		/* Create Param module version */
		createParamVersion(data, moduleEntity);

	}

	@Override
	@HistorizeDataPerform(action = ActionSystemEnum.UPDATE)
	public void applyVersionToParamModule(String moduleIdentifier, String versionIdentifier) {
		final ModuleRef moduleEntity = getModuleByIdentifier(Optional.of(moduleIdentifier), Boolean.TRUE, Boolean.TRUE);
		final VersionRef versionEntityFound = findVersionRefOfModule(moduleEntity, versionIdentifier);
		doSaveParamVersionOfModule(moduleEntity, versionEntityFound);
	}

	@Override
	@HistorizeDataPerform(action = ActionSystemEnum.UPDATE)
	public void applyEndpointToParamModule(String moduleIdentifier, UUID routeId, String versionIdentifier)
			throws BusinessException {
		final ModuleRef moduleEntity = getModuleByIdentifier(Optional.of(moduleIdentifier), Boolean.TRUE, Boolean.TRUE);
		final VersionRef versionEntityFound = findVersionRefOfModule(moduleEntity, versionIdentifier);
		final EndpointRef routeEntityFound = findEndpointRefOfModule(routeId, moduleEntity);
		doSaveParamVersionOfModule(versionEntityFound, routeEntityFound);
	}

	@Override
	@Transactional(readOnly = true)
	public ParamVersionListDto getParamVersionOfModule(final String moduleIdentifier, final String versionIdentifier) {

		final ModuleRef moduleEntity = getModuleByIdentifier(Optional.of(moduleIdentifier), Boolean.TRUE, Boolean.TRUE);
		List<VersionRef> versionEntities = retrieveListVersionsByModule(moduleEntity);
		if (CollectionUtils.isNotEmpty(versionEntities) && StringUtils.isNotBlank(versionIdentifier)) {

			try {

				UUID versionId = UUID.fromString(StringUtils.trimToEmpty(versionIdentifier));
				versionEntities = ServiceUtils.safeList(versionEntities).stream() //
						.filter(v -> versionId.equals(v.getId())) //
						.collect(Collectors.toList());

			} catch (IllegalArgumentException e) {

				String versionNumber = StringUtils.trim(versionIdentifier);
				versionEntities = ServiceUtils.safeList(versionEntities) //
						.stream() //
						.filter(v -> versionNumber.equals(v.getVersionNumber())) //
						.collect(Collectors.toList());
			}

		}

		// TODO do mapper
		ParamVersionListDto result = new ParamVersionListDto();
		ModuleInfoDto module = new ModuleInfoDto();
		module.setId(moduleEntity.getId());
		module.setCode(moduleEntity.getCode());
		module.setLabel(moduleEntity.getLabel());
		result.setModule(module);

		List<VersionListDto> orderedVersions = ServiceUtils.safeList(versionEntities).stream() //
				.map(produceVersionFromParam()) //
				.sorted(Comparator.comparing(VersionListDto::getVersionNumber, Comparator.reverseOrder()))
				.collect(Collectors.toList());

		result.setVersions(orderedVersions);
		return result;

	}

	@Override
	@HistorizeDataPerform(action = ActionSystemEnum.CREATE)
	public void createParamVersion(final ParamVersionRequest request, final ModuleRef moduleEntity) {

		if (request.isEnableOnParam()) {

			Set<VersionRef> versionRefsEntities = moduleEntity.getVersionRefs();
			Set<EndpointRef> endpointRefsEntities = moduleEntity.getEndpointRefs();
			Set<EndpointRef> routeEntitiesToApplyParam = endpointRefsEntities;

			List<VersionRef> versionEntitiesToApplyParam = ServiceUtils.safeList(request.getVersions())
					.stream()
					.map(VersionRefRequest::getVersionNumber)
					.map(v -> ServiceUtils.safeSet(versionRefsEntities).stream()
							.filter(item -> v.equals(item.getVersionNumber())).findFirst().orElse(null))
					.filter(Objects::nonNull).collect(Collectors.toList());

			if (!request.isAppendEndpoints()) {
				routeEntitiesToApplyParam = ServiceUtils.safeList(request.getEndpoints()).stream()
						.map(EndpointRefRequest::getUrl)
						.map(r -> ServiceUtils.safeSet(endpointRefsEntities).stream()
								.filter(e -> r.equalsIgnoreCase(e.getUrl())).findFirst().orElse(null))
						.filter(Objects::nonNull)
						.collect(Collectors.toSet());

			}

			final Set<EndpointRef> finalRouteEntitiesToApplyParam = routeEntitiesToApplyParam;

			List<MonitoringParamVersion> listOfParamToSave = ServiceUtils
					.safeList(versionEntitiesToApplyParam).stream()
					.map(v -> finalRouteEntitiesToApplyParam.stream().map(newParamOfEndpoint(v)).collect(Collectors.toList()))
					.flatMap(List::stream).collect(Collectors.toList());

			apiVersionParamRepository.saveAll(listOfParamToSave);

		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<ModuleRefDto> searchModules(final String code) {

		List<ModuleRef> result = StringUtils.isNotBlank(code) ? //
				moduleRefRepository.findByCodeContaining(code) : //
				moduleRefRepository.findAll();
		return mapper.moduleRefToDtos(result);

	}

	@Override
	@HistorizeDataPerform(action = ActionSystemEnum.CREATE)
	public ModuleInfoDto createSingleModule(final ModuleInfoRequest moduleDto) {

		log.info("Start - Save new module {}", writeObjectAsString(moduleDto));
		List<ModuleInfoDto> modulesSaved = createMultipleModule(Collections.singletonList(moduleDto));
		return modulesSaved.get(0);

	}

	@Override
	@HistorizeDataPerform(action = ActionSystemEnum.CREATE)
	public ModuleRef createSingleEntityModule(final ModuleInfoRequest moduleDto) {

		log.info("Start - Save new module {}", writeObjectAsString(moduleDto));
		List<ModuleRef> modulesSaved = createMultipleEntityModule(Collections.singletonList(moduleDto));
		return modulesSaved.get(0);

	}

	@Override
	public List<ModuleInfoDto> createMultipleModule(final List<ModuleInfoRequest> moduleList) {

		List<ModuleRef> modulesSaved = createMultipleEntityModule(moduleList);
		return mapper.moduleRefToInfoDtos(modulesSaved);

	}

	@Override
	public List<ModuleRef> createMultipleEntityModule(final List<ModuleInfoRequest> moduleList) {

		log.info("Start - Save new modules {}", writeObjectAsString(moduleList));
		String moduleExists = ServiceUtils.safeList(moduleList) //
				.stream() //
				.map(ModuleInfoRequest::getCode) //
				.map(String::toUpperCase) //
				.filter(moduleRefRepository::existsByCode) //
				.findFirst().orElse(null);
		if (moduleExists != null) {
			log.error(
					String.format(TranslatorProvider.getMsg(BundleConstants.MDM_MODULE_CODE_DUPLICATED), moduleExists));
			throw new ModuleCodeDuplicateException(
					String.format(TranslatorProvider.getMsg(BundleConstants.MDM_MODULE_CODE_DUPLICATED), moduleExists));
		}
		List<ModuleRef> entities = mapper.moduleInfoReqToEntities(moduleList);
		return moduleRefRepository.saveAll(entities);

	}

	@Override
	@HistorizeDataPerform(action = ActionSystemEnum.CREATE)
	public void createEndpoints(final ModuleRef moduleEntity, final List<EndpointRefRequest> endpoints)
			throws BusinessException {

		Set<EndpointRef> endpointRefs = moduleEntity.getEndpointRefs();

		if (CollectionUtils.isEmpty(endpointRefs) && CollectionUtils.isEmpty(endpoints)) {
			log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_ENDPOINT_MANDATORY),
					moduleEntity.getCode()));
			throw new BusinessException(
					String.format(TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_ENDPOINT_MANDATORY),
							moduleEntity.getCode()));
		}

		List<String> pathEndpoints = ServiceUtils.safeSet(endpointRefs)
				.stream()
				.map(EndpointRef::getUrl)
				.collect(Collectors.toList());

		String pathCheckerFound = ServiceUtils.safeList(endpoints)
				.stream()
				.map(EndpointRefRequest::getUrl)
				.map(StringUtils::trimToNull)
				.filter(StringUtils::isNotEmpty)
				.filter(pathEndpoints::contains)
				.findFirst().orElse(null);

		if (pathCheckerFound != null) {
			log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_ENDPOINT_URL_DUPLICATED),
					moduleEntity.getCode(), pathCheckerFound));
			throw new EndpointUrlDuplicateException(
					String.format(TranslatorProvider.getMsg(BundleConstants.MDM_ENDPOINT_URL_DUPLICATED),
							moduleEntity.getCode(), pathCheckerFound));
		}

		ServiceUtils.safeList(endpoints)
				.stream()
				.map(mapper::endpointRequestToEntity)
				.forEach(moduleEntity::addEndpointRef);

	}

	@Override
	public void createEndpoints(final Optional<String> moduleIdentifier, final List<EndpointRefRequest> endpoints)
			throws BusinessException {

		if (moduleIdentifier.isEmpty()) {
			log.error(TranslatorProvider.getMsg(BundleConstants.MDM_MODULE_ENDPOINT_MANDATORY));
			throw new BusinessException(TranslatorProvider.getMsg(BundleConstants.MDM_MODULE_ENDPOINT_MANDATORY));
		}

		log.info("Start - Save Endpoints [Module = {}] [Endpoints : {}]", moduleIdentifier.get(),
				writeObjectAsString(endpoints));

		final ModuleRef moduleEntity = getModuleByIdentifier(moduleIdentifier, Boolean.TRUE, Boolean.TRUE);

		createEndpoints(moduleEntity, endpoints);

	}

	@Override
	@HistorizeDataPerform(action = ActionSystemEnum.CREATE)
	public void createVersions(final ModuleRef moduleEntity, final List<VersionRefRequest> versions) {

		Set<VersionRef> versionRefs = moduleEntity.getVersionRefs();
		List<String> versionNumbers = ServiceUtils.safeSet(versionRefs)
				.stream()
				.map(VersionRef::getVersionNumber)
				.collect(Collectors.toList());

		String versionFound = ServiceUtils.safeList(versions)
				.stream()
				.map(VersionRefRequest::getVersionNumber)
				.map(StringUtils::trimToEmpty)
				.filter(StringUtils::isNoneEmpty)
				.filter(versionNumbers::contains)
				.findFirst().orElse(null);

		if (versionFound != null) {
			log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_NUMBER_DUPLICATED),
					moduleEntity.getCode(), versionFound));
			throw new VersionNumberDuplicateException(
					String.format(TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_NUMBER_DUPLICATED),
							moduleEntity.getCode(), versionFound));
		}

		ServiceUtils.safeList(versions)
				.stream()
				.map(mapper::versionRequestToEntity)
				.forEach(moduleEntity::addVersionRef);

	}

	@Override
	public void createVersions(final Optional<String> moduleIdentifier, final List<VersionRefRequest> versions)
			throws BusinessException {

		if (moduleIdentifier.isEmpty()) {
			log.error(TranslatorProvider.getMsg(BundleConstants.MDM_MODULE_VERSION_MANDATORY));
			throw new BusinessException(TranslatorProvider.getMsg(BundleConstants.MDM_MODULE_VERSION_MANDATORY));
		}

		log.info("Start - Save Version [Module = {}] [Version : {}]", moduleIdentifier.get(),
				writeObjectAsString(versions));

		final ModuleRef moduleEntity = getModuleByIdentifier(moduleIdentifier, Boolean.TRUE, Boolean.TRUE);

		createVersions(moduleEntity, versions);

	}

	@Override
	@HistorizeDataPerform(action = ActionSystemEnum.UPDATE)
	public void lockingHandleParamVersion(final UUID paramId,
										  final UUID routeId,
										  final UUID versionId,
							  			  final Boolean lock) throws BusinessException {

		if (Objects.nonNull(paramId)) {

			MonitoringParamVersion entity = apiVersionParamRepository.findById(paramId).orElseThrow(() -> {
				log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_ID_NOT_FOUND),
						paramId));
				return new EntityNotFoundException(String.format(
						TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_ID_NOT_FOUND), paramId));
			});

			lockingHandleParamVersion(entity, lock);

		} else {

			List<MonitoringParamVersion> paramEntities = findParamByRouteAndVersion(routeId, versionId);
			for (MonitoringParamVersion p : paramEntities) {
				lockingHandleParamVersion(p, lock);
			}

		}

	}

	@Override
	@HistorizeDataPerform(action = ActionSystemEnum.UPDATE)
	public void activationVersionOrEndpoint(UUID routeId, UUID versionId, boolean useCache) {

		if (Objects.nonNull(routeId)) {
			activationEndpoint(routeId, versionId, useCache);
		} else if (Objects.nonNull(versionId)) {
			activationVersion(versionId, useCache);
		}

	}

	@Override
	public void activationEndpoint(final UUID routeId, final UUID versionId, boolean useCache) {

		EndpointRef routeEntity = getEndpointById(routeId, useCache);

		if (routeEntity.isActive()) {
			log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_ENDPOINT_ALREADY_ACTIVE),
					routeId));
			throw new EntityNotFoundException(String.format(
					TranslatorProvider.getMsg(BundleConstants.MDM_ENDPOINT_ALREADY_ACTIVE), routeId));
		}

		routeEntity.setActive(Boolean.TRUE);
		routeEntity.setDateActivation(ZonedDateTime.now());

		List<MonitoringParamVersion> paramEntities = findParamByRouteAndVersion(routeId, versionId);

		ServiceUtils.safeList(paramEntities)
				.stream()
				.filter(Objects::nonNull)
				.forEach(p -> p.setActive(p.getVersionRef().isActive() && routeEntity.isActive()));

		evictCacheKeyFromCache(routeId.toString(), EndpointRef.cacheEntityName(), EndpointRef.class);

	}

	@Override
	public void activationVersion(final UUID versionId, boolean useCache) {

		VersionRef versionEntity = getVersionById(versionId, useCache);

		if (versionEntity.isActive()) {
			log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_ALREADY_ACTIVE),
					versionId));
			throw new EntityNotFoundException(String.format(
					TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_ALREADY_ACTIVE), versionId));
		}

		versionEntity.setActive(Boolean.TRUE);
		versionEntity.setDateActivation(ZonedDateTime.now());

		List<MonitoringParamVersion> paramEntities = apiVersionParamRepository.findByVersionToActivate(versionId);

		ServiceUtils.safeList(paramEntities).stream().filter(Objects::nonNull)
				.forEach(p -> p.setActive(versionEntity.isActive() && p.getEndpointRef().isActive()));

		evictCacheKeyFromCache(versionId.toString(), VersionRef.cacheEntityName(), VersionRef.class);

	}

	@Override
	@HistorizeDataPerform(action = ActionSystemEnum.DELETE)
	public void deleteModule(Optional<String> moduleIdentifier) {

		final ModuleRef moduleEntity = getModuleByIdentifier(moduleIdentifier, Boolean.TRUE, Boolean.FALSE);
		ModuleRef moduleCloned = SerializationUtils.clone(moduleEntity);

		/* Delete switch flag to TRUE in ModuleRef */
		moduleEntity.setDeleted(Boolean.TRUE);
		moduleEntity.setCode(
				String.format("%s_%s", moduleEntity.getCode(), RandomStringUtils.randomAlphanumeric(10).toUpperCase()));

		/* Delete switch flag to TRUE in EndpointRef */
		Set<EndpointRef> endpointRefs = moduleEntity.getEndpointRefs();
		ServiceUtils.safeSet(endpointRefs).forEach(this::deleteEndPoint);

		/* Delete switch flag to TRUE in VersionRef */
		Set<VersionRef> versionRefs = moduleEntity.getVersionRefs();
		ServiceUtils.safeSet(versionRefs).forEach(this::deleteVersion);

		/* Evict cache key moduleCode */
		evictCacheKeyFromCache(moduleCloned.getCode(), ModuleRef.cacheEntityName(), ModuleRef.class);

	}

	@Override
	public void deleteEndPoint(final UUID id) {

		EndpointRef routeEntity = getEndpointById(id);
		/* Delete switch flag to TRUE in EndpointRef */
		deleteEndPoint(routeEntity);
	}

	@Override
	public void deleteVersion(final UUID id) {

		VersionRef versionEntity = getVersionById(id);
		/* Delete switch flag to TRUE in VersionRef */
		deleteVersion(versionEntity);
	}

	@Override
	public void purgeApiParam() {

		apiVersionParamRepository.purgeApiParam();

		versionRefRepository.purgeVersion();

		endpointRefRepository.purgeEndpoint();

		moduleRefRepository.purgeModule();

		/* clear all caches */
		cacheComponentUtils.evictAllCaches();
	}

	/* Private method */
	private void deleteVersion(VersionRef version) {

		/* Set version deleted */
		version.setDeleted(Boolean.TRUE);

		/* Evict cache key version id */
		evictCacheKeyFromCache(version.getId().toString(), VersionRef.cacheEntityName(), VersionRef.class);

		/* Delete switch flag to TRUE in ApiVersionParam */
		deleteApiVersionByVersion(version);
	}

	private void deleteEndPoint(EndpointRef endpoint) {

		/* Set route deleted */
		endpoint.setDeleted(Boolean.TRUE);

		/* Evict cache key route id */
		evictCacheKeyFromCache(endpoint.getId().toString(), EndpointRef.cacheEntityName(), EndpointRef.class);

		/* Delete switch flag to TRUE in ApiVersionParam */
		deleteApiVersionByEndPoint(endpoint);
	}

	private void lockingHandleParamVersion(MonitoringParamVersion entity, Boolean indicatorLock) throws BusinessException {

		Optional.ofNullable(entity).orElseThrow(() -> new IllegalArgumentException("Param Entity is mandatory"));

		if (entity.isLock() == indicatorLock) {

			throw new BusinessException(String.format(
					indicatorLock == EndpointLocking.LOCK.getValue()
							? TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_ALREADY_LOCKED)
							: TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_ALREADY_UNLOCKED),
					entity.getId().toString()));

		}

		entity.setLock(!entity.isLock());
		entity.setDateLock(entity.isLock() ? ZonedDateTime.now() : null);

	}

	private void deleteApiVersionByEndPoint(EndpointRef endpoint) {
		List<MonitoringParamVersion> paramEntities = apiVersionParamRepository.findByEndpoint(endpoint.getId());
		ServiceUtils.safeList(paramEntities).forEach(p -> p.setDeleted(Boolean.TRUE));
	}

	private void deleteApiVersionByVersion(VersionRef version) {
		List<MonitoringParamVersion> paramEntities = apiVersionParamRepository.findByVersion(version.getId());
		ServiceUtils.safeList(paramEntities).forEach(p -> p.setDeleted(Boolean.TRUE));
	}

	private ModuleRef getModuleByIdentifier(final Optional<String> moduleIdentifier,
											boolean throwException,
											boolean useCache) {

		String moduleCode = moduleIdentifier.orElse(StringUtils.EMPTY);
		ModuleRef moduleEntity = getEntityFromCache(ModuleRef.class, moduleCode, ModuleRef.cacheEntityName(), useCache);
		if (moduleEntity != null) {
			return moduleEntity;
		}

		moduleEntity = proceedGetModule(throwException).apply(moduleCode);

		/* save module in cache */
		saveEntityInCache(moduleEntity, moduleCode, ModuleRef.cacheEntityName(), useCache);

		return moduleEntity;

	}

	private Function<String, ModuleRef> proceedGetModule(boolean throwException) {

		return moduleIdentifier -> {

			String errorMsg;
			ModuleRef moduleEntity;

			try {

				UUID moduleId = UUID.fromString(StringUtils.trimToEmpty(moduleIdentifier));
				log.info("try to find module by [UUID = {}]", moduleId);
				moduleEntity = moduleRefRepository.findById(moduleId).orElse(null);
				errorMsg = String.format(TranslatorProvider.getMsg(BundleConstants.MDM_MODULE_ID_NOT_FOUND),
						moduleId);

			} catch (IllegalArgumentException e) {

				log.info("try to find module by [CODE = {}]", moduleIdentifier);
				moduleEntity = moduleRefRepository.findByCode(moduleIdentifier).orElse(null);
				errorMsg = String.format(TranslatorProvider.getMsg(BundleConstants.MDM_MODULE_CODE_NOT_FOUND),
						moduleIdentifier);

			}

			if (Objects.isNull(moduleEntity) && throwException) {
				log.error(errorMsg);
				throw new EntityNotFoundException(errorMsg);
			}

			return moduleEntity;
		};

	}

	private VersionRef getVersionById(final UUID versionId) {
		return versionRefRepository.findById(versionId).orElseThrow(() -> {
			log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_ID_NOT_FOUND),
					versionId));
			return new EntityNotFoundException(String
					.format(TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_ID_NOT_FOUND), versionId));
		});
	}

	private VersionRef getVersionById(final UUID versionId, boolean useCache) {

		VersionRef versionEntity = getEntityFromCache(VersionRef.class,
				versionId.toString(), VersionRef.cacheEntityName(), useCache);

		if (versionEntity != null) {
			return versionEntity;
		}

		versionEntity = versionRefRepository.findById(versionId).orElseThrow(() -> {
			log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_ID_NOT_FOUND),
					versionId));
			return new EntityNotFoundException(String
					.format(TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_ID_NOT_FOUND), versionId));
		});

		/* save in cache */
		saveEntityInCache(versionEntity, versionId.toString(), VersionRef.cacheEntityName(), useCache);

		return versionEntity;

	}

	private EndpointRef getEndpointById(final UUID routeId) {
		return endpointRefRepository.findById(routeId).orElseThrow(() -> {
			log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_ENDPOINT_ID_NOT_FOUND),
					routeId));
			return new EntityNotFoundException(String
					.format(TranslatorProvider.getMsg(BundleConstants.MDM_ENDPOINT_ID_NOT_FOUND), routeId));
		});
	}

	private EndpointRef getEndpointById(final UUID routeId, boolean useCache) {

		EndpointRef endpointEntity = getEntityFromCache(EndpointRef.class, routeId.toString(), EndpointRef.cacheEntityName(), useCache);

		if (endpointEntity != null) {
			return endpointEntity;
		}

		endpointEntity = endpointRefRepository.findById(routeId).orElseThrow(() -> {
			log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_ENDPOINT_ID_NOT_FOUND),
					routeId));
			return new EntityNotFoundException(String
					.format(TranslatorProvider.getMsg(BundleConstants.MDM_ENDPOINT_ID_NOT_FOUND), routeId));
		});

		/* save module in cache */
		saveEntityInCache(endpointEntity, routeId.toString(), EndpointRef.cacheEntityName(), useCache);

		return endpointEntity;
	}

	private Function<VersionRef, VersionListDto> produceVersionFromParam() {
		return version -> {

			List<MonitoringParamVersion> paramVersionEntities = apiVersionParamRepository
					.findByVersionRefAndActiveTrueAndLockFalse(version);

			List<String> urls = ServiceUtils.safeList(paramVersionEntities).stream() //
					.map(MonitoringParamVersion::getEndpointRef) //
					.map(EndpointRef::getUrl) //
					.collect(Collectors.toList());

			VersionListDto versionItem = new VersionListDto();
			versionItem.setId(version.getId());
			versionItem.setVersionNumber(version.getVersionNumber());
			versionItem.setEndpoints(urls);

			return versionItem;
		};
	}

	private EndpointRef findEndpointRefOfModule(UUID routeId, final ModuleRef moduleEntity) {

		final Set<EndpointRef> endpointRefs = moduleEntity.getEndpointRefs();
		return ServiceUtils.safeSet(endpointRefs).stream().filter(e -> e.getId().equals(routeId)).findFirst()
				.orElseThrow(() -> {
					String message = String.format(
							TranslatorProvider.getMsg(BundleConstants.MDM_ROUTE_NOT_FOUND_IN_MODULE),
							routeId.toString(), moduleEntity.getCode());
					log.error(message);
					return new EntityNotFoundException(message);
				});

	}

	private VersionRef findVersionRefOfModule(final ModuleRef moduleEntity, final String versionIdentifier) {

		final Set<VersionRef> versionRefs = moduleEntity.getVersionRefs();
		try {

			log.info("try to get version [UUID {}] of module [CODE = {}]", versionIdentifier, moduleEntity.getCode());
			final UUID versionId = UUID.fromString(StringUtils.trimToEmpty(versionIdentifier));
			final Predicate<VersionRef> findById = v -> v.getId().equals(versionId);
			return getVersionInModuleOrThrowException(versionRefs, findById, versionIdentifier, moduleEntity.getCode());

		} catch (final IllegalArgumentException e) {

			log.info("try to get version [Number {}] of module [CODE = {}]", versionIdentifier, moduleEntity.getId());
			final Predicate<VersionRef> findByVersionNumber = v -> v.getVersionNumber().equals(versionIdentifier);
			return getVersionInModuleOrThrowException(versionRefs, findByVersionNumber, versionIdentifier,
					moduleEntity.getCode());

		}

	}

	private VersionRef getVersionInModuleOrThrowException(Set<VersionRef> versionRefs,
			Predicate<VersionRef> versionCondition, String version, String module) {

		return ServiceUtils.safeSet(versionRefs)
				.stream()
				.filter(versionCondition)
				.findFirst()
				.orElseThrow(() -> {
					String message = String.format(
							TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_NOT_FOUND_IN_MODULE),
							version, module);
					log.error(message);
					return new EntityNotFoundException(message);
				});

	}

	private void doSaveParamVersionOfModule(ModuleRef moduleEntity, VersionRef versionEntityFounded) {

		Set<EndpointRef> endpointRefs = moduleEntity.getEndpointRefs();

		List<MonitoringParamVersion> apiParamVersionToSave = ServiceUtils.safeSet(endpointRefs).stream()
				.map(newParamOfEndpoint(versionEntityFounded)).collect(Collectors.toList());

		apiVersionParamRepository.saveAll(apiParamVersionToSave);

	}

	private void doSaveParamVersionOfModule(VersionRef versionEntityFound, EndpointRef routeEntityFound)
			throws BusinessException {

		MonitoringParamVersion paramVersionExist = apiVersionParamRepository.findByVersionRefAndEndpointRef(versionEntityFound,
				routeEntityFound);

		if (Objects.nonNull(paramVersionExist)) {

			if (!paramVersionExist.isActive()) {
				log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_NOT_ACTIVE),
						paramVersionExist.getId()));
				throw new BusinessException(
						String.format(TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_NOT_ACTIVE),
								paramVersionExist.getId()));
			}

			if (paramVersionExist.isLock()) {
				log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_IS_LOCKED),
						paramVersionExist.getId()));
				throw new BusinessException(
						String.format(TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_IS_LOCKED),
								paramVersionExist.getId()));
			}

			log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_ALREADY_EXIST),
					paramVersionExist.getId()));
			throw new BusinessException(
					String.format(TranslatorProvider.getMsg(BundleConstants.MDM_PARAM_VERSION_ALREADY_EXIST),
							paramVersionExist.getId()));

		}

		MonitoringParamVersion apiParamVersionToSave = new MonitoringParamVersion()//
				.setEndpointRef(routeEntityFound)
				.setActive(versionEntityFound.isActive())
				.setLock(EndpointLocking.UNLOCK.getValue())
				.setVersionRef(versionEntityFound);

		apiVersionParamRepository.save(apiParamVersionToSave);

//		// Produce message
//		producer.notifyChangeToAuth(getClassInfo(), ServiceUtils.getCurrentMethodName(),
//				KEY_REFRESH_PARAM);

	}

	private Function<EndpointRef, MonitoringParamVersion> newParamOfEndpoint(VersionRef version) {

		return endpoint -> new MonitoringParamVersion()
				.setEndpointRef(endpoint)
				.setActive(version.isActive() && endpoint.isActive())
				.setLock(Boolean.FALSE)
				.setVersionRef(version);

	}

	private List<VersionRef> retrieveListVersionsByModule(
			final ModuleRef module) {
		return versionRefRepository.findByModuleRef(module).orElse(Collections.emptyList());
	}

	private List<MonitoringParamVersion> findParamByRouteAndVersion(final UUID routeId, final UUID versionId) {
		return Optional.ofNullable(versionId)
				.map(v -> Collections.singletonList(apiVersionParamRepository.getOneByEndpointAndVersion(routeId, v)))
				.orElseGet(() -> apiVersionParamRepository.findByEndpoint(routeId));
	}

	//	private List<VersionRef> findVersionByModuleIdentifier(final Optional<String> moduleIdentifier) {
//
//		List<VersionRef> versionEntities;
//		String moduleCode = moduleIdentifier.orElse(StringUtils.EMPTY);
//
//		try {
//
//			UUID moduleId = UUID.fromString(StringUtils.trimToEmpty(moduleCode));
//			log.info("try to find version by module [UUID = {}]", moduleId);
//			versionEntities = versionRefRepository.findByModuleRef_Id(moduleId).orElseThrow(() -> {
//				log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_MODULE_ID_NOT_FOUND),
//						moduleId));
//				return new EntityNotFoundException(String.format(
//						TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_MODULE_ID_NOT_FOUND), moduleId));
//			});
//
//		} catch (IllegalArgumentException e) {
//
//			log.info("Try to find version by module [CODE = {}]", moduleCode);
//			versionEntities = versionRefRepository.findByModuleRef_Code(moduleCode).orElseThrow(() -> {
//				log.error(String.format(TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_MODULE_CODE_NOT_FOUND),
//						moduleCode));
//				return new EntityNotFoundException(String.format(
//						TranslatorProvider.getMsg(BundleConstants.MDM_VERSION_MODULE_CODE_NOT_FOUND), moduleCode));
//			});
//
//		}
//		return versionEntities;
//
//	}

}