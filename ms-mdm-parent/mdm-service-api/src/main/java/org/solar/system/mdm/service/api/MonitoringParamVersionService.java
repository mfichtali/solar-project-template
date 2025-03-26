package org.solar.system.mdm.service.api;

import org.solar.system.central.common.all.exceptions.BusinessException;
import org.solar.system.central.common.mdm.dto.ModuleInfoDto;
import org.solar.system.central.common.mdm.dto.ModuleRefDto;
import org.solar.system.central.common.mdm.dto.ParamVersionListDto;
import org.solar.system.central.common.mdm.request.EndpointRefRequest;
import org.solar.system.central.common.mdm.request.ModuleInfoRequest;
import org.solar.system.central.common.mdm.request.VersionRefRequest;
import org.solar.system.mdm.model.entities.pmversion.ModuleRef;
import org.solar.system.mdm.model.request.ParamVersionRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MonitoringParamVersionService {

	List<ModuleRefDto> searchModules(final String code);
	ParamVersionListDto getParamVersionOfModule(final String moduleIdentifier, final String versionIdentifier);
	ModuleRef getOrCreateModuleRef(final ModuleInfoRequest dto, boolean useCache);
	
	/** Start Creation service **/
	ModuleInfoDto createSingleModule(final ModuleInfoRequest dto);
	ModuleRef createSingleEntityModule(final ModuleInfoRequest dto);
	List<ModuleInfoDto> createMultipleModule(final List<ModuleInfoRequest> dto);
	List<ModuleRef> createMultipleEntityModule(final List<ModuleInfoRequest> dto);

	void createEndpoints(final Optional<String> moduleIdentifier, final List<EndpointRefRequest> endpoints) throws BusinessException;

	void createEndpoints(final ModuleRef moduleEntity, final List<EndpointRefRequest> endpoints) throws BusinessException;

	void createVersions(final Optional<String> moduleIdentifier, final List<VersionRefRequest> versions) throws BusinessException;

	void createVersions(final ModuleRef moduleEntity, final List<VersionRefRequest> versions);

	void createParamVersion(final ParamVersionRequest data, final ModuleRef moduleEntity);

	void quickNewParamModule(ParamVersionRequest data, boolean useCache) throws BusinessException;
	/** End Creation service  **/

    void lockingHandleParamVersion(final UUID paramId, final UUID routeId, final UUID versionId, final Boolean lock) throws BusinessException;
	
    /** Start Activation service **/
	void activationVersionOrEndpoint(final UUID routeId, final UUID versionId, boolean useCache);
	void activationEndpoint(final UUID routeId, final UUID versionId, boolean useCache);
	void activationVersion(final UUID versionId, boolean useCache);
	/** End Activation service **/
	
	/** Start Delete Action **/
	void deleteModule(final Optional<String> moduleIdentifier);
    void deleteEndPoint(final UUID id);
    void deleteVersion(final UUID id);
    /** End Delete Action **/

	void applyVersionToParamModule(final String moduleIdentifier, final String versionIdentifier);
	void applyEndpointToParamModule(final String moduleIdentifier, final UUID routeId, final String versionIdentifier) throws BusinessException;
	
	/** Purge module & endpoint & version & api param version */
	void purgeApiParam();

}
