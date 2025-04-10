package org.solar.system.hexa.infra.api.feign.mdm;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.solar.system.central.common.vehicle.containers.PmVehicleContainer;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MdmVehicleFallbackFactory implements FallbackFactory<MdmVehicleClientFeign> {

    @Override
    public MdmVehicleClientFeign create(Throwable cause) {
        log.error("An exception occurred when calling the MdmVehicleClientFeign", cause);
        return new MdmVehicleClientFeign() {
            @Override
            public PmVehicleContainer.VehicleBasicInfoRecord loadVehicleBasicInfoByRegistrationNumber(final String registrationNumber) {
                log.error("MdmVehicleClientFeign#getVehicleBasicInfoByRegistrationNumber fallback , exception", cause);
                if (cause instanceof FeignException fe) {
                    throw fe;
                }
                return null;
            }

            @Override
            public PmVehicleContainer.VehicleBasicInfoRecord loadVehicleBasicInfoByIdentifier(final String vehicleId) {
                log.error("MdmVehicleClientFeign#getVehicleBasicInfoByIdentifier fallback , exception", cause);
                if (cause instanceof FeignException fe) {
                    throw fe;
                }
                return null;
            }
        };
    }
}
