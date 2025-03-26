package org.solar.system.mdm.model.repository;

import org.solar.system.mdm.model.entities.vehicle.ScheduleTaskTxOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ScheduleTaskTxOutboxRepository extends JpaRepository<ScheduleTaskTxOutbox, UUID> {

    List<ScheduleTaskTxOutbox> findByProcessedFalseOrderByCreationDateAsc();

    List<ScheduleTaskTxOutbox> findByEntityNameAndProcessedFalseOrderByCreationDateAsc(String entityName);

}
