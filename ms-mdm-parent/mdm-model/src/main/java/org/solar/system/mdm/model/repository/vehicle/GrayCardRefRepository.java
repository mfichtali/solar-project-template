package org.solar.system.mdm.model.repository.vehicle;

import org.solar.system.mdm.model.entities.vehicle.GrayCardRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GrayCardRefRepository extends JpaRepository<GrayCardRef, UUID> {

    boolean existsBySerialOperationNumber(String serialOperationNumber);

}
