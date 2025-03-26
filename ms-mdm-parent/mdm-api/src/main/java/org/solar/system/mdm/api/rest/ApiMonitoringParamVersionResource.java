package org.solar.system.mdm.api.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.solar.system.central.common.all.exceptions.BusinessException;
import org.solar.system.central.common.all.pojo.ErrorWrapper;
import org.solar.system.central.common.all.utils.ConstantUtils;
import org.solar.system.central.common.mdm.Views;
import org.solar.system.central.common.mdm.dto.ModuleInfoDto;
import org.solar.system.central.common.mdm.dto.ModuleRefDto;
import org.solar.system.central.common.mdm.dto.ParamVersionListDto;
import org.solar.system.central.common.mdm.enums.MdmLanguageSupported;
import org.solar.system.central.common.mdm.request.EndpointRefRequest;
import org.solar.system.central.common.mdm.request.ModuleInfoRequest;
import org.solar.system.central.common.mdm.request.VersionRefRequest;
import org.solar.system.mdm.common.enums.EndpointLocking;
import org.solar.system.mdm.model.annotations.EndpointUniqueConstraint;
import org.solar.system.mdm.model.annotations.ModuleUniqueConstraint;
import org.solar.system.mdm.model.annotations.NotEmptyCollectionConstraint;
import org.solar.system.mdm.model.annotations.VersionUniqueConstraint;
import org.solar.system.mdm.model.request.ParamVersionRequest;
import org.solar.system.mdm.service.api.MonitoringParamVersionService;
import org.solar.system.mdm.service.config.TranslatorProvider;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.solar.system.central.common.all.utils.HttpConstUtils.HEADER_ACCEPT_LANGUAGE;
import static org.solar.system.central.common.all.utils.HttpConstUtils.MEDIA_APPLICATION_JSON;
import static org.solar.system.mdm.api.rest.AbstractResource.MDM_PARAM_VERSION_REQUEST_MAPPING;
import static org.solar.system.mdm.api.rest.AbstractResource.PARAM_VERSION_RESOURCE;
import static org.solar.system.mdm.common.utils.BundleConstants.MDM_BAD_REQUEST_LOCKING;


@Validated
@RestController(PARAM_VERSION_RESOURCE)
@RequestMapping(value = MDM_PARAM_VERSION_REQUEST_MAPPING)
@Tag(name = "Monitoring Param version Resource", description = "The Monitoring Param version API")
@RequiredArgsConstructor
public class ApiMonitoringParamVersionResource extends AbstractResource {

	private final MonitoringParamVersionService apiVersionParamService;

	@Operation(summary = "Get list of modules (filtered by code, if code is present)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Module list", content = {
			@Content(mediaType = MEDIA_APPLICATION_JSON, schema = @Schema(implementation = ModuleRefDto.class)) }) })
	@GetMapping("/modules")
	@ResponseStatus(HttpStatus.OK)
	public List<ModuleRefDto> searchRefModules(
			@RequestParam(value = "moduleIdentifier", required = false) String code) {
		return apiVersionParamService.searchModules(code);
	}

	@Operation(summary = "Get Param version of module information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Data param version", content = {
			@Content(mediaType = MEDIA_APPLICATION_JSON, schema = @Schema(implementation = ParamVersionListDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Param version not found", content = {
					@Content(mediaType = MEDIA_APPLICATION_JSON, schema = @Schema(implementation = ErrorWrapper.class)) }) })
	@GetMapping("/get")
	@ResponseStatus(HttpStatus.OK)
	@JsonView(Views.NoIdentifierView.class)
	public ParamVersionListDto getParamVersionOfModule(
			@Parameter(name = "moduleIdentifier", description = "Module Identifier (ID or CODE)") @RequestParam(value = "moduleIdentifier") String moduleIdentifier,
			@Parameter(name = "versionIdentifier", description = "Version Identifier ID or VALUE (1.0, 1.3, ...)") @RequestParam(value = "versionIdentifier", required = false) String versionIdentifier) {
		return apiVersionParamService.getParamVersionOfModule(moduleIdentifier, versionIdentifier);
	}

	@Operation(summary = "Create single new module")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Module created", content = {
			@Content(mediaType = MEDIA_APPLICATION_JSON, schema = @Schema(implementation = ModuleInfoDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Error creating module", content = {
					@Content(mediaType = MEDIA_APPLICATION_JSON, schema = @Schema(implementation = ErrorWrapper.class)) }) })
	@PostMapping("/module")
	@ResponseStatus(HttpStatus.CREATED)
	public ModuleInfoDto createRefModule(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Module Information", content = @Content(schema = @Schema(implementation = ModuleInfoRequest.class))) @RequestBody @Valid ModuleInfoRequest moduleRef) {
		return apiVersionParamService.createSingleModule(moduleRef);
	}

	@Operation(summary = "Create multiple modules")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Modules created", content = {
			@Content(mediaType = MEDIA_APPLICATION_JSON, schema = @Schema(implementation = ModuleInfoDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Error creating modules", content = {
					@Content(mediaType = MEDIA_APPLICATION_JSON, schema = @Schema(implementation = ErrorWrapper.class)) }) })
	@PostMapping("/modules")
	@ResponseStatus(HttpStatus.CREATED)
	public List<ModuleInfoDto> createRefModules(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Module Information", content = @Content(schema = @Schema(implementation = ModuleInfoRequest.class)))
			@RequestBody @NotEmptyCollectionConstraint @ModuleUniqueConstraint List<@Valid ModuleInfoRequest> moduleRefs) {
		return apiVersionParamService.createMultipleModule(moduleRefs);
	}

	@PostMapping("/module/{moduleIdentifier}/endpoints")
	@ResponseStatus(HttpStatus.CREATED)
	public void createRefEndpoints(@PathVariable Optional<String> moduleIdentifier,
								   @RequestBody @NotEmptyCollectionConstraint @EndpointUniqueConstraint List<@Valid EndpointRefRequest> endpoints)
			throws BusinessException {
		apiVersionParamService.createEndpoints(moduleIdentifier, endpoints);
	}

	@PostMapping("/module/{moduleIdentifier}/versions")
	@ResponseStatus(HttpStatus.CREATED)
	public void createRefVersions(@PathVariable Optional<String> moduleIdentifier,
								  @RequestBody @NotEmptyCollectionConstraint @VersionUniqueConstraint List<@Valid VersionRefRequest> versions)
			throws BusinessException {
		apiVersionParamService.createVersions(moduleIdentifier, versions);
	}

	@PostMapping("/new-module")
	@ResponseStatus(HttpStatus.CREATED)
	public void createApiVersionProcess(
			@RequestParam(value = ConstantUtils.RQ_USE_CACHE, required = false, defaultValue = "true") boolean useCache,
			@Valid @RequestBody ParamVersionRequest data, BindingResult result) throws BusinessException {
		apiVersionParamService.quickNewParamModule(data, useCache);
	}

	@PutMapping("/apply-version")
	@ResponseStatus(HttpStatus.CREATED)
	public void applyVersionOfModuleToParam(@RequestParam(value = "moduleIdentifier") String moduleIdentifier,
			@RequestParam(value = "versionNumber") String versionNumber) {
		apiVersionParamService.applyVersionToParamModule(moduleIdentifier, versionNumber);
	}

	@PutMapping("/apply-endpoint")
	@ResponseStatus(HttpStatus.CREATED)
	public void applyEndpointOfModuleToParam(@RequestParam(value = "moduleIdentifier") String moduleIdentifier,
			@RequestParam(value = "versionNumber") String versionNumber, @RequestParam(value = "routeId") UUID routeId)
			throws BusinessException {
		apiVersionParamService.applyEndpointToParamModule(moduleIdentifier, routeId, versionNumber);
	}

	@PostMapping("/endpoint/lock")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void lockHandelApiVersion(@RequestParam(value = "paramId", required = false) UUID paramId,
			@RequestParam(value = "routeId", required = false) UUID routeId,
			@RequestParam(value = "versionId", required = false) UUID versionId) throws BusinessException {

		if (Objects.isNull(paramId) && Objects.isNull(routeId)) {
			throw new BusinessException(TranslatorProvider.getMsg(MDM_BAD_REQUEST_LOCKING));
		}
		apiVersionParamService.lockingHandleParamVersion(paramId, routeId, versionId, EndpointLocking.LOCK.getValue());
	}

	@PostMapping("/endpoint/unlock")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unlockHandelApiVersion(@RequestParam(value = "paramId", required = false) UUID paramId,
			@RequestParam(value = "routeId", required = false) UUID routeId,
			@RequestParam(value = "versionId", required = false) UUID versionId) throws BusinessException {

		if (Objects.isNull(paramId) && Objects.isNull(routeId)) {
			throw new BusinessException(TranslatorProvider.getMsg(MDM_BAD_REQUEST_LOCKING));
		}
		apiVersionParamService.lockingHandleParamVersion(paramId, routeId, versionId,
				EndpointLocking.UNLOCK.getValue());
	}

	@PostMapping("/endpoint/activation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activationEndpoint(@RequestParam(value = "routeId", required = false) UUID routeId,
			@RequestParam(value = "versionId", required = false) UUID versionId,
			@RequestParam(value = ConstantUtils.RQ_USE_CACHE, required = false, defaultValue = "true") boolean useCache) {
		apiVersionParamService.activationVersionOrEndpoint(routeId, versionId, useCache);
	}

	@Operation(summary = "Delete Module cascading on endpoints and versions")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Module deleted"),
			@ApiResponse(responseCode = "404", description = "Module not found", content = {
					@Content(mediaType = MEDIA_APPLICATION_JSON, schema = @Schema(implementation = ErrorWrapper.class)) }) })
	@DeleteMapping("/module/{moduleIdentifier}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteModule(@PathVariable Optional<String> moduleIdentifier,
			@Schema(implementation = MdmLanguageSupported.class) @RequestHeader(name = HEADER_ACCEPT_LANGUAGE, required = false) String language) {
		apiVersionParamService.deleteModule(moduleIdentifier);
	}

	@Operation(summary = "Delete Endpoint")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Endpoint deleted"),
			@ApiResponse(responseCode = "404", description = "Endpoint not found", content = {
					@Content(mediaType = MEDIA_APPLICATION_JSON, schema = @Schema(implementation = ErrorWrapper.class)) }) })
	@DeleteMapping("/endpoint/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEndpoint(@PathVariable UUID id,
			@Schema(implementation = MdmLanguageSupported.class) @RequestHeader(name = HEADER_ACCEPT_LANGUAGE, required = false) String language) {
		apiVersionParamService.deleteEndPoint(id);
	}

	@Operation(summary = "Delete Version")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Version deleted"),
			@ApiResponse(responseCode = "404", description = "Version not found", content = {
					@Content(mediaType = MEDIA_APPLICATION_JSON, schema = @Schema(implementation = ErrorWrapper.class)) }) })
	@DeleteMapping("/version/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteVersion(@PathVariable UUID id,
			@Schema(implementation = MdmLanguageSupported.class) @RequestHeader(name = HEADER_ACCEPT_LANGUAGE, required = false) String language) {
		apiVersionParamService.deleteVersion(id);
	}

	@Hidden
	@DeleteMapping("/purge")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void purge() {
		apiVersionParamService.purgeApiParam();
	}

}
