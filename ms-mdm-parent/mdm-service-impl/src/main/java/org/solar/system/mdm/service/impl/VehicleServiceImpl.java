package org.solar.system.mdm.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.solar.system.central.common.all.enums.ActionSystemEnum;
import org.solar.system.central.common.all.enums.DocumentTypeEnum;
import org.solar.system.central.common.all.enums.KafkaKeyLabelEnum;
import org.solar.system.central.common.all.exceptions.BusinessException;
import org.solar.system.central.common.all.exceptions.TechnicalException;
import org.solar.system.central.common.all.messaging.MonoDataKF;
import org.solar.system.central.common.all.pojo.RecordUpdateField;
import org.solar.system.central.common.all.pojo.RecordWrapper;
import org.solar.system.central.common.all.utils.DateUtils;
import org.solar.system.central.common.all.utils.DocumentUtils;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.solar.system.central.common.vehicle.dto.VehicleBasicInfoDto;
import org.solar.system.central.common.vehicle.dto.VehicleDto;
import org.solar.system.central.common.vehicle.enums.EvenementVehicleTypeEnum;
import org.solar.system.central.common.vehicle.messaging.CreateHistoricalVehicleMessage;
import org.solar.system.central.common.vehicle.messaging.CreateInspectionVehicleMessage;
import org.solar.system.central.common.vehicle.messaging.CreateTSAVVehicleMessage;
import org.solar.system.central.common.vehicle.request.CalendarBillingRequest;
import org.solar.system.central.common.vehicle.request.CautionBillingRequest;
import org.solar.system.mdm.common.exception.RegistrationNumberAlreadyDefinedException;
import org.solar.system.mdm.model.bo.vehicle.GrayCardBo;
import org.solar.system.mdm.model.bo.vehicle.VehicleBasicInfoBo;
import org.solar.system.mdm.model.bo.vehicle.VehicleBo;
import org.solar.system.mdm.model.entities.pmbilling.CalendarBillingRef;
import org.solar.system.mdm.model.entities.pmbilling.CautionBillingRef;
import org.solar.system.mdm.model.entities.vehicle.BlobStorage;
import org.solar.system.mdm.model.entities.vehicle.GrayCardRef;
import org.solar.system.mdm.model.entities.vehicle.HistoricalVehicle;
import org.solar.system.mdm.model.entities.vehicle.VehicleRef;
import org.solar.system.mdm.model.repository.pmbilling.CalendarBillingRefRepository;
import org.solar.system.mdm.model.repository.pmbilling.CautionBillingRefRepository;
import org.solar.system.mdm.model.repository.vehicle.BlobStorageRepository;
import org.solar.system.mdm.model.repository.vehicle.GrayCardRefRepository;
import org.solar.system.mdm.model.repository.vehicle.HistoricalVehicleRepository;
import org.solar.system.mdm.model.repository.vehicle.VehicleRefRepository;
import org.solar.system.mdm.model.request.VehicleDocumentRequest;
import org.solar.system.mdm.model.request.VehicleInfoRequest;
import org.solar.system.mdm.model.request.VehicleInspectionDocumentRequest;
import org.solar.system.mdm.model.request.VehicleTSAVDocumentRequest;
import org.solar.system.mdm.service.api.ScheduleTaskTxOutboxService;
import org.solar.system.mdm.service.api.VehicleService;
import org.solar.system.mdm.service.base.AbstractService;
import org.solar.system.mdm.service.config.TranslatorProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import static org.solar.system.central.common.all.messaging.KafkaGroupHeaderEnum.MDM_2_AUDIT;
import static org.solar.system.central.common.all.messaging.KafkaGroupHeaderEnum.MDM_2_RENTAL;
import static org.solar.system.mdm.common.utils.BundleConstants.*;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleServiceImpl extends AbstractService implements VehicleService {

    private final VehicleRefRepository vehicleRepo;
    private final ScheduleTaskTxOutboxService scheduleTaskTxOutboxService;
    private final HistoricalVehicleRepository historicalVehicleRepo;
    private final CautionBillingRefRepository cautionBillingRepo;
    private final CalendarBillingRefRepository calendarBillingRepo;
    private final GrayCardRefRepository grayCardRefRepo;
    private final BlobStorageRepository blobStorageRepository;

    @Override
    public VehicleDto getVehicleInfo(String registrationNumber, Boolean fullCalendar, boolean useCache) {

        String rnUsed = getRegistrationNumberValue(registrationNumber, useCache);
        String cacheKey = generateVehicleCacheKey(rnUsed, fullCalendar);
        VehicleRef vehicleCacheEntity = getEntityFromCache(VehicleRef.class, cacheKey, VehicleRef.cacheEntityName(), useCache);
        if (vehicleCacheEntity != null) {
            return mapper.vehicleRefToDto(vehicleCacheEntity);
        }

        if(fullCalendar == null || fullCalendar == Boolean.FALSE) {
            Session session = entityManager.unwrap(Session.class);
            Filter filter = session.enableFilter(VehicleRef.CURRENT_CALENDAR_BILLING);
            filter.setParameter(VehicleRef.DEF_FILTER_CURRENT_YEAR, DateUtils.getCurrentYear());
        }

        VehicleRef vehicleEntity = vehicleRepo.getByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> {
                    String errMsg = String.format(TranslatorProvider.getMsg(MDM_VEHICLE_ENTITY_NOT_FOUND), registrationNumber);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });

        String errMsg;
        if(CollectionUtils.isEmpty(vehicleEntity.getCalendarBillings())) {
            errMsg = String.format(TranslatorProvider.getMsg(MDM_CALENDAR_BILLING_NOT_APPLIED),
                    vehicleEntity.getRegistrationReference());
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }

        if(CollectionUtils.isEmpty(vehicleEntity.getCautionBillings())) {
            errMsg = String.format(TranslatorProvider.getMsg(MDM_CAUTION_BILLING_NOT_APPLIED),
                    vehicleEntity.getRegistrationReference());
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }

        /* save module in cache */
        saveEntityInCache(vehicleEntity, generateVehicleCacheKey(vehicleEntity.getRegistrationReference(), fullCalendar), VehicleRef.cacheEntityName(), useCache);
        return mapper.vehicleRefToDto(vehicleEntity);

    }

    @Override
    public VehicleBasicInfoDto getVehicleBasicInfo(final String vehicleTechnicalId, final String rnVehicle) {

        VehicleBasicInfoBo vehicleBo = null;
        if (StringUtils.isNotBlank(vehicleTechnicalId)) {
            vehicleBo = vehicleRepo.getVehicleBasicInfoByIdentifier(ServiceUtils.safeUUIDFromString(vehicleTechnicalId))
                    .orElseThrow(() -> {
                        String errMsg = String.format(TranslatorProvider.getMsg(MDM_VEHICLE_ENTITY_IDENTIFIER_NOT_FOUND), vehicleTechnicalId);
                        log.error(errMsg);
                        return new EntityNotFoundException(errMsg);
                    });
        }
        if (StringUtils.isNotBlank(rnVehicle)) {
            vehicleBo = vehicleRepo.getVehicleBasicInfoByRegistrationNumber(rnVehicle)
                    .orElseThrow(() -> {
                        String errMsg = String.format(TranslatorProvider.getMsg(MDM_VEHICLE_ENTITY_NOT_FOUND), rnVehicle);
                        log.error(errMsg);
                        return new EntityNotFoundException(errMsg);
                    });
        }
        return mapper.vehicleBoToDto(vehicleBo);
    }

    @Override
    public void createHistoricalEventVehicle(MonoDataKF<CreateHistoricalVehicleMessage> message) {
        CreateHistoricalVehicleMessage hist = message.getData();
        VehicleRef vehicleEntity = vehicleRepo.getByRegistrationNumber(hist.getRegistrationNumber())
                .orElseThrow(() -> {
                    String errMsg = String.format(TranslatorProvider.getMsg(MDM_VEHICLE_ENTITY_NOT_FOUND),
                            hist.getRegistrationNumber());
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        createHistoricalEventVehicle(vehicleEntity, hist.getEventType(),
                String.format("[P%dO%d] %s", message.getPartition(), message.getOffset(), hist.getEventType().getEventDescription()),
                hist.getStartEvent(), hist.getEndEvent());
    }

    @Override
    public void createVehicle(VehicleInfoRequest request) {

        /* validation request */
        VehicleBo vehicleBo = request.getVehicle();
        GrayCardBo grayCardBo = request.getGrayCard();

        VehicleRef vehicleEntity = mapper.vehicleBoToEntity(vehicleBo);
        GrayCardRef grayCardEntity = mapper.grayCardBoToEntity(grayCardBo);
        vehicleEntity.setGrayCard(grayCardEntity);

        HistoricalVehicle hv = new HistoricalVehicle()
                .setVehicle(vehicleEntity)
                .setEventType(EvenementVehicleTypeEnum.ICV)
                .setSummaryEvent(EvenementVehicleTypeEnum.ICV.getEventDescription())
                .setStartEventDate(LocalDateTime.now())
                .setEndEventDate(LocalDateTime.now());
        vehicleEntity.attachHistoricalVehicles(hv);

        VehicleRef savedVehicle = saveOrUpdateVehicle(vehicleEntity);
        scheduleTaskTxOutboxService.storeScheduleActionTx(() -> mapper.vehicleRefToTxOutboxMapper(savedVehicle, ActionSystemEnum.CREATE, this::writeObjectAsString),
                KafkaKeyLabelEnum.KEY_RENTAL_MANAGE_VEHICLE, Set.of(MDM_2_AUDIT.name()), classInfoFullName(), ServiceUtils.getCurrentMethodName());

    }

    @Override
    public void applyFinalRegistrationNumber(String wwRegNumber, String finalRegNumber, boolean useCache) throws BusinessException {

        if(StringUtils.isBlank(finalRegNumber)) {
            log.error(TranslatorProvider.getMsg(MDM_VEHICLE_RN_MANDATORY));
            throw new BusinessException(TranslatorProvider.getMsg(MDM_VEHICLE_RN_MANDATORY));
        }

        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter(VehicleRef.CURRENT_CALENDAR_BILLING);
        filter.setParameter(VehicleRef.DEF_FILTER_CURRENT_YEAR, DateUtils.getCurrentYear());

        VehicleRef vehicleEntity = vehicleRepo.findByWwRegistrationNumber(wwRegNumber)
                .orElseThrow(() -> {
                    String errMsg = String.format(TranslatorProvider.getMsg(MDM_VEHICLE_ENTITY_NOT_FOUND),
                            wwRegNumber);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });

        if(StringUtils.isNotBlank(vehicleEntity.getRegistrationNumber()) ||
                Objects.nonNull(vehicleEntity.getDateChangeWwRegistration())) {
            throw new RegistrationNumberAlreadyDefinedException(String.format(TranslatorProvider.getMsg(MDM_VEHICLE_ALREADY_RN_DEFINED),
                    wwRegNumber));
        }

        vehicleEntity.setRegistrationNumber(finalRegNumber);
        vehicleEntity.setDateChangeWwRegistration(LocalDate.now());

        VehicleRef savedVehicle = saveOrUpdateVehicle(vehicleEntity);
        createHistoricalEventVehicle(vehicleEntity, EvenementVehicleTypeEnum.CRP);

        /* store message for change registration number of vehicle **/
        scheduleTaskTxOutboxService.storeScheduleActionTx(() -> mapper.vehicleRefToTxOutboxMapper(savedVehicle, ActionSystemEnum.UPDATE, this::writeObjectAsString),
                KafkaKeyLabelEnum.KEY_RENTAL_MANAGE_VEHICLE, Set.of(MDM_2_AUDIT.name()), classInfoFullName(), ServiceUtils.getCurrentMethodName());

        RecordUpdateField<UUID> recordUpdateVehicle = new RecordUpdateField<UUID>()
                .setEntityName(VehicleRef.class.getSimpleName())
                .setEntityId(vehicleEntity.getId())
                .setFieldName("registrationNumber")
                .setOldValue(wwRegNumber)
                .setNewValue(finalRegNumber);

        scheduleTaskTxOutboxService.storeScheduleActionTx(() -> mapper.recordUpdateFieldVehicleToTxOutboxMapper(recordUpdateVehicle, ActionSystemEnum.UPDATE, this::writeObjectAsString),
                KafkaKeyLabelEnum.KEY_RENTAL_MANAGE_VEHICLE, Set.of(MDM_2_AUDIT.name(), MDM_2_RENTAL.name()), classInfoFullName(), ServiceUtils.getCurrentMethodName());

        String cacheKeyFalse = generateVehicleCacheKey(vehicleEntity.getRegistrationReference(), Boolean.FALSE);
        String cacheKeyTrue = generateVehicleCacheKey(vehicleEntity.getRegistrationReference(), Boolean.TRUE);
        evictCacheKeyFromCache(cacheKeyFalse, VehicleRef.cacheEntityName(), VehicleRef.class);
        evictCacheKeyFromCache(cacheKeyTrue, VehicleRef.cacheEntityName(), VehicleRef.class);
        saveEntityInCache(vehicleEntity, cacheKeyFalse, VehicleRef.cacheEntityName(), useCache);

    }

    @Override
    public void defineCalendarBilling(String registrationNumber, String appliedYear, List<CalendarBillingRequest> calendarBillingRequests, boolean useCache) throws BusinessException {

        validationApplyCalendarBilling(calendarBillingRequests, registrationNumber, appliedYear);
        VehicleRef vehicleEntity = vehicleRepo.getByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> {
                    String errMsg = String.format(TranslatorProvider.getMsg(MDM_VEHICLE_ENTITY_NOT_FOUND),
                            registrationNumber);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        List<CalendarBillingRef> calendarEntities = mapper.calendarBillingRequestToEntities(calendarBillingRequests);
        vehicleEntity.mergeCalendarBillings(calendarEntities);

        saveOrUpdateVehicle(vehicleEntity);
        createHistoricalEventVehicle(vehicleEntity, EvenementVehicleTypeEnum.DCB);

        String cacheKeyFalse = generateVehicleCacheKey(vehicleEntity.getRegistrationReference(), Boolean.FALSE);
        String cacheKeyTrue = generateVehicleCacheKey(vehicleEntity.getRegistrationReference(), Boolean.TRUE);
        evictCacheKeyFromCache(cacheKeyFalse, VehicleRef.cacheEntityName(), VehicleRef.class);
        evictCacheKeyFromCache(cacheKeyTrue, VehicleRef.cacheEntityName(), VehicleRef.class);
        saveEntityInCache(vehicleEntity, cacheKeyTrue, VehicleRef.cacheEntityName(), useCache);

    }

    @Override
    public void duplicateCalendarBilling(String registrationNumber, String fromYear, String toYear) {

        validationCopyCalendarBilling(fromYear, toYear, registrationNumber);
        List<CalendarBillingRef> calendarBillingEntities = calendarBillingRepo.findByVehicleAndYear(registrationNumber, fromYear);
        List<CalendarBillingRef> calendarsToPersist = calendarBillingEntities.stream()
                .map(mapper::copyCalendarBilling)
                .map(c -> c.setYear(toYear))
                .collect(Collectors.toList());

        calendarBillingRepo.saveAll(calendarsToPersist);
        createHistoricalEventVehicle(registrationNumber, EvenementVehicleTypeEnum.CCB);

        String cacheKeyTrue = generateVehicleCacheKey(registrationNumber, Boolean.TRUE);
        String cacheKeyFalse = generateVehicleCacheKey(registrationNumber, Boolean.FALSE);
        evictCacheKeyFromCache(cacheKeyTrue, VehicleRef.cacheEntityName(), VehicleRef.class);
        evictCacheKeyFromCache(cacheKeyFalse, VehicleRef.cacheEntityName(), VehicleRef.class);

    }

    @Override
    public void defineCautionBilling(String registrationNumber, CautionBillingRequest cautionBillingRequest, boolean useCache) {

        VehicleRef vehicleEntity = vehicleRepo.getByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> {
                    String errMsg = String.format(TranslatorProvider.getMsg(MDM_VEHICLE_ENTITY_NOT_FOUND),
                            registrationNumber);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });

        CautionBillingRef cautionEntity = mapper.cautionBillingRequestToEntity(cautionBillingRequest);

        vehicleEntity.attachCautionBilling(cautionEntity);
        saveOrUpdateVehicle(vehicleEntity);
        createHistoricalEventVehicle(vehicleEntity, EvenementVehicleTypeEnum.DCT);

        String cacheKeyTrue = generateVehicleCacheKey(vehicleEntity.getRegistrationReference(), Boolean.TRUE);
        String cacheKeyFalse = generateVehicleCacheKey(vehicleEntity.getRegistrationReference(), Boolean.FALSE);
        evictCacheKeyFromCache(cacheKeyFalse, VehicleRef.cacheEntityName(), VehicleRef.class);
        evictCacheKeyFromCache(cacheKeyTrue, VehicleRef.cacheEntityName(), VehicleRef.class);
        saveEntityInCache(vehicleEntity, cacheKeyTrue, VehicleRef.cacheEntityName(), useCache);

    }

    @Override
    public String uploadVehicleDocument(MultipartFile file, VehicleDocumentRequest documentRequest) {
        return uploadVehicleDocuments(file, documentRequest, null);
    }

    @Override
    public String uploadInspectionVehicleDocument(MultipartFile file, VehicleInspectionDocumentRequest inspectionDocumentRequest) {

        return uploadVehicleDocuments(file, inspectionDocumentRequest, vid -> {
            /* store message for upload document of inspection vehicle -> RENTAL **/
            final CreateInspectionVehicleMessage createInspectionVehicleMessage = mapper.inspectionRequestToMessage(vid);
            scheduleTaskTxOutboxService.storeScheduleActionTx(
                    () -> mapper.stringFeedToTxOutboxMapper(writeObjectAsString(createInspectionVehicleMessage), ActionSystemEnum.UPDATE,
                            CreateInspectionVehicleMessage.class.getSimpleName()),
                    KafkaKeyLabelEnum.KEY_RENTAL_MANAGE_VEHICLE, Set.of(MDM_2_RENTAL.name()), classInfoFullName(),
                    ServiceUtils.getCurrentMethodName());
        });

    }

    @Override
    public String uploadTSAVVehicleDocument(MultipartFile file, VehicleTSAVDocumentRequest tsavDocumentRequest) {
        return uploadVehicleDocuments(file, tsavDocumentRequest, tsav -> {
            /* store message for upload document of tsav vehicle -> RENTAL **/
            final CreateTSAVVehicleMessage createTSAVVehicleMessage = mapper.tsavRequestToMessage(tsav);
            scheduleTaskTxOutboxService.storeScheduleActionTx(
                    () -> mapper.stringFeedToTxOutboxMapper(writeObjectAsString(createTSAVVehicleMessage), ActionSystemEnum.UPDATE,
                            CreateTSAVVehicleMessage.class.getSimpleName()),
                    KafkaKeyLabelEnum.KEY_RENTAL_MANAGE_VEHICLE, Set.of(MDM_2_RENTAL.name()), classInfoFullName(),
                    ServiceUtils.getCurrentMethodName());
        });
    }

    @Override
    public Pair<String, byte[]> downloadDocumentVehicle(String regNumber, String docType) {

        return blobStorageRepository.findByObjectReferenceAndDocumentType(regNumber, DocumentTypeEnum.valueOf(docType))
                .map(blob -> {
                    try {
                        String filename = String.format("%s-%s-%s.%s", regNumber, docType, DateUtils.toStringFormat(LocalDate.now()),
                                blob.getDocumentExtension());
                        byte[] bytes = DocumentUtils.decompressDocument(blob.getBlobContent());
                        return Pair.of(filename, bytes);
                    } catch (DataFormatException | IOException exception) {
                        throw new ContextedRuntimeException("Error downloading a document", exception)
                                .addContextValue("Document ID", blob.getId())
                                .addContextValue("Document Name", blob.getDocumentName());
                    }
                })
                .orElseThrow(() -> {
                    String errMsg = String.format(TranslatorProvider.getMsg(MDM_DOCUMENT_ENTITY_NOT_FOUND),
                            regNumber);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });

    }

    @Override
    public void getVehicleDocumentInfo(String rnVehicle, String docType) {

        if (StringUtils.isNotBlank(docType)) {
            DocumentTypeEnum documentType = DocumentTypeEnum.valueOf(docType);
            BlobStorage blobStorage = blobStorageRepository.findByObjectReferenceAndDocumentType(rnVehicle, documentType)
                    .orElseThrow(() -> {
                        String errMsg = String.format(TranslatorProvider.getMsg(MDM_DOCUMENT_ENTITY_NOT_FOUND),
                                rnVehicle);
                        log.error(errMsg);
                        return new EntityNotFoundException(errMsg);
                    });
        } else {
            
        }

    }

    /*
     * Private method
     */

    /**
     * @param calendarBillingRequests
     * @param appliedYear
     * @throws BusinessException
     */
    private void validationApplyCalendarBilling(final List<CalendarBillingRequest> calendarBillingRequests, final String registrationNumber,
                                           final String appliedYear) throws BusinessException {
        String errMsg;
        if(CollectionUtils.isEmpty(calendarBillingRequests)) {
            errMsg = TranslatorProvider.getMsg(MDM_CALENDAR_BILLING_REQ_MANDATORY);
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }

        if(calendarBillingRequests.size() < 12) {
            errMsg = TranslatorProvider.getMsg(MDM_CALENDAR_BILLING_REQ_INCOMPLETE);
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }

        if(StringUtils.isBlank(appliedYear)) {
            errMsg = TranslatorProvider.getMsg(MDM_CALENDAR_BILLING_REQ_YEAR_MANDATORY);
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }

        List<String> yearList = calendarBillingRequests.stream()
                .map(CalendarBillingRequest::getYear)
                .collect(Collectors.toList());
        boolean isSameYear = yearList.stream().allMatch(y -> Objects.equals(y, appliedYear));

        if(!isSameYear) {
            errMsg = TranslatorProvider.getMsg(MDM_CALENDAR_BILLING_CHECK_APPLY_YEAR);
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }

        if (calendarBillingRepo.existsCalendarByVehicleIdAndYear(registrationNumber, appliedYear)) {
            errMsg = String.format(TranslatorProvider.getMsg(MDM_CALENDAR_BILLING_ALREADY_EXISTS),
                    appliedYear);
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }
    }

    private String getRegistrationNumberValue(String registrationNumber, boolean useCache) {

        String rnCached = getObjectFromCache(String.class, registrationNumber, VehicleRef.cacheEntityName(), useCache);
        if (rnCached != null) {
            return rnCached;
        }
        String rnEntity = vehicleRepo.getUsedRegistrationNumber(registrationNumber);
        saveObjectInCache(rnEntity, registrationNumber, VehicleRef.cacheEntityName(), useCache);
        return rnEntity;

    }

    private void validationCopyCalendarBilling(final String fromYear, final String toYear, final String registrationNumber) {

        String errMsg;
        if(StringUtils.isBlank(toYear)) {
            errMsg = TranslatorProvider.getMsg(MDM_CALENDAR_BILLING_REQ_YEAR_MANDATORY);
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }

        if(Objects.equals(fromYear, toYear)) {
            errMsg = String.format(TranslatorProvider.getMsg(MDM_CALENDAR_BILLING_SAME_YEAR),
                    fromYear);
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }

        if (!calendarBillingRepo.existsCalendarByVehicleIdAndYear(registrationNumber, fromYear)) {
            errMsg = String.format(TranslatorProvider.getMsg(MDM_CALENDAR_BILLING_OF_YEAR_NOT_EXISTS),
                    fromYear);
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }

        if (calendarBillingRepo.existsCalendarByVehicleIdAndYear(registrationNumber, toYear)) {
            errMsg = String.format(TranslatorProvider.getMsg(MDM_CALENDAR_BILLING_ALREADY_EXISTS),
                    toYear);
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }

    }

    private String generateVehicleCacheKey(String regNumber, Boolean flag) {
        if(flag == null) flag = Boolean.FALSE;
        return String.format("%s#%s", regNumber, flag);
    }

    private void createHistoricalEventVehicle(String registrationNumber, EvenementVehicleTypeEnum event) {
        VehicleRef vehicleEntity = vehicleRepo.findByRegistrationNumber(registrationNumber);
        createHistoricalEventVehicle(vehicleEntity, event);
    }

    private void createHistoricalEventVehicle(VehicleRef vehicleEntity, EvenementVehicleTypeEnum event) {
        createHistoricalEventVehicle(vehicleEntity, event, null, LocalDateTime.now(), LocalDateTime.now());
    }

    private void createHistoricalEventVehicle(VehicleRef vehicleEntity, EvenementVehicleTypeEnum event, String description,
                                              LocalDateTime startEvent, LocalDateTime endEvent) {
        LocalDateTime now = LocalDateTime.now();
        HistoricalVehicle lastHistoricalHVehicle = vehicleEntity.getLastHistoricalHVehicle();
        if(lastHistoricalHVehicle != null && lastHistoricalHVehicle.getEndEventDate() == null) {
            lastHistoricalHVehicle.setEndEventDate(Optional.ofNullable(startEvent).orElse(now));
            saveOrUpdateHistoricalVehicle(lastHistoricalHVehicle);
        }
        HistoricalVehicle newHist = new HistoricalVehicle()
                .setVehicle(vehicleEntity)
                .setEventType(event)
                .setSummaryEvent(StringUtils.isNotBlank(description) ? description : event.getEventDescription())
                .setStartEventDate(Optional.ofNullable(startEvent).orElse(now))
                .setEndEventDate(Optional.ofNullable(endEvent).orElse(now));
        saveOrUpdateHistoricalVehicle(newHist);
    }

    private void saveOrUpdateHistoricalVehicle(HistoricalVehicle historicalVehicle) {
        historicalVehicleRepo.save(historicalVehicle);
    }

    private VehicleRef saveOrUpdateVehicle(VehicleRef vehicleEntity) {
        return vehicleRepo.save(vehicleEntity);
    }

    private void documentUpdateValidity(VehicleDocumentRequest documentRequest) {
        if (Objects.isNull(documentRequest.getObjectIdentifier())
                || StringUtils.isBlank(documentRequest.getObjectReference())
                || Objects.isNull(documentRequest.getDocumentType())) {
            throw new BusinessException("Upload document failed: Document information is required");
        }

        final UUID objectIdentifier = documentRequest.getObjectIdentifier();
        final String objectReference = documentRequest.getObjectReference();
        final VehicleBasicInfoDto vehicleBasicInfo = getVehicleBasicInfo(objectIdentifier.toString(), objectReference);
        if (Objects.isNull(vehicleBasicInfo)) {
            throw new BusinessException("Upload document failed: Vehicle not found");
        } else {
            documentRequest.setObjectIdentifier(vehicleBasicInfo.getVehicleId());
            documentRequest.setObjectReference(vehicleBasicInfo.getRegistrationNumber());
        }
    }

    private <T extends VehicleDocumentRequest> String uploadVehicleDocuments(MultipartFile file, T documentRequest, Consumer<T> consumer) {

        documentUpdateValidity(documentRequest);
        try {
            BlobStorage documentBlob = new BlobStorage()
                    .setObjectIdentifier(documentRequest.getObjectIdentifier())
                    .setObjectReference(documentRequest.getObjectReference())
                    .setObjectName(documentRequest.getDocumentType().getObjectName())
                    .setObjectDomain(documentRequest.getDocumentType().getObjectDomain())
                    .setDocumentType(documentRequest.getDocumentType())
                    .setDocumentName(DocumentUtils.getFileNameWithoutExtension(file.getOriginalFilename()))
                    .setDocumentExtension(DocumentUtils.getFileExtension(file.getOriginalFilename()))
                    .setBlobContent(DocumentUtils.compressDocument(file.getBytes()));
            final BlobStorage blobSaved = blobStorageRepository.save(documentBlob);
            documentRequest.setBlobStorageId(blobSaved.getId());

            /* produce message for upload new document of vehicle **/
            RecordWrapper record = new RecordWrapper().setEntityName(BlobStorage.class.getSimpleName())
                    .setEntityIdentifier(documentBlob.getObjectIdentifier().toString())
                    .setActionName(String.format("Upload new document of vehicle [%s](%s)", documentBlob.getObjectReference(),
                            documentBlob.getDocumentType().name()));
            scheduleTaskTxOutboxService.storeScheduleActionTx(
                    () -> mapper.recordWrapperToTxOutboxMapper(record, ActionSystemEnum.UPDATE, this::writeObjectAsString),
                    KafkaKeyLabelEnum.KEY_RENTAL_MANAGE_VEHICLE, Set.of(MDM_2_AUDIT.name()), classInfoFullName(),
                    ServiceUtils.getCurrentMethodName());

            if (consumer != null)
                consumer.accept(documentRequest);

            return String.format(TranslatorProvider.getMsg(MDM_DOCUMENT_UPLOAD_SUCCESSFUL), documentBlob.getDocumentName());
        } catch (IOException e) {
            throw new TechnicalException(e.getMessage());
        }
    }
}
