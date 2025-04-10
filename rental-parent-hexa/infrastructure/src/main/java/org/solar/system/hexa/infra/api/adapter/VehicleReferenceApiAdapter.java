package org.solar.system.hexa.infra.api.adapter;

import lombok.RequiredArgsConstructor;
import org.solar.system.central.common.vehicle.containers.PmVehicleContainer;
import org.solar.system.hexa.domain.port.out.VehicleReferenceApiPort;
import org.solar.system.hexa.infra.api.feign.mdm.MdmVehicleClientFeign;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VehicleReferenceApiAdapter implements VehicleReferenceApiPort {

    private final MdmVehicleClientFeign mdmVehicleClientFeign;

    @Override
    public Optional<PmVehicleContainer.VehicleBasicInfoRecord> loadVehicleInfoByRegistrationNumber(final String rnVehicle) {
        return Optional.ofNullable(mdmVehicleClientFeign.loadVehicleBasicInfoByRegistrationNumber(rnVehicle));
    }
}
