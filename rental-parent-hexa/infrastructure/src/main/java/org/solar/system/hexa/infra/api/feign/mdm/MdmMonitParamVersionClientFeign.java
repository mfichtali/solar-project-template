package org.solar.system.hexa.infra.api.feign.mdm;

import org.solar.system.central.common.mdm.containers.PmModuleContainer;
import org.solar.system.hexa.infra.api.feign.common.AbstractClientFeign;
import org.solar.system.hexa.infra.api.feign.common.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.solar.system.hexa.infra.api.feign.common.AbstractClientFeign.MDM_SERVICE_NAME;
import static org.solar.system.hexa.infra.api.feign.common.AbstractClientFeign.MDM_SERVICE_URL;


@FeignClient(
        name = MDM_SERVICE_NAME,
        url = MDM_SERVICE_URL,
        configuration = FeignSupportConfig.class,
        fallbackFactory = MdmMonitParamVersionFallbackFactory.class)
public interface MdmMonitParamVersionClientFeign extends AbstractClientFeign {

    String PARAM_VERSION_GET = "/api/pm-module/details";

    @GetMapping(PARAM_VERSION_GET)
    @ResponseStatus(HttpStatus.OK)
    PmModuleContainer.ModuleVersionCollectionRecord getParamVersionByModule(
            final @RequestParam(value = "moduleIdentifier") String moduleIdentifier,
            final @RequestParam(value = "versionIdentifier", required = false) String versionIdentifier);

}
