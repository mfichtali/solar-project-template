package org.solar.system.hexa.infra.api.feign.mdm;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.solar.system.central.common.mdm.containers.PmModuleContainer;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MdmMonitParamVersionFallbackFactory implements FallbackFactory<MdmMonitParamVersionClientFeign> {

    @Override
    public MdmMonitParamVersionClientFeign create(Throwable cause) {
        log.error("An exception occurred when calling the MdmMonitParamVersionClientFeign", cause);
        return new MdmMonitParamVersionClientFeign() {

            @Override
            public PmModuleContainer.ModuleVersionCollectionRecord getParamVersionByModule(String moduleIdentifier, String versionIdentifier) {
                log.error("MdmMonitParamVersionClientFeign#getParamVersionByModule fallback , exception", cause);
                if (cause instanceof FeignException fe) {
                    throw fe;
                }
                return null;
            }
        };
    }

}
