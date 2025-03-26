package org.solar.system.mdm.model.repository.vehicle;

import org.solar.system.mdm.model.entities.vehicle.HistoricalVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HistoricalVehicleRepository extends JpaRepository<HistoricalVehicle, UUID> {
}
