package org.solar.system.hexa.domain.port.out;

import org.solar.system.central.common.vehicle.containers.PmVehicleContainer;

import java.util.Optional;

public interface VehicleReferenceApiPort {

    Optional<PmVehicleContainer.VehicleBasicInfoRecord> loadVehicleInfoByRegistrationNumber(String rnVehicle);
}
