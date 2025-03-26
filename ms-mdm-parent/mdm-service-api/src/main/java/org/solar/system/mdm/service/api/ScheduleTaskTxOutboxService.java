package org.solar.system.mdm.service.api;

import org.solar.system.central.common.all.enums.ActionSystemEnum;
import org.solar.system.central.common.all.enums.KafkaKeyLabelEnum;
import org.solar.system.mdm.model.entities.vehicle.ScheduleTaskTxOutbox;

import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

public interface ScheduleTaskTxOutboxService {
    
    void storeScheduleActionTx(Supplier<ScheduleTaskTxOutbox> supplierMapper, KafkaKeyLabelEnum codeKafkaKey,
                          Set<String> groupsToDelivery, String classInfoFullName, String methodName);

    void storeScheduleActionTx(String payloadContent, ActionSystemEnum actionSystemEnum, KafkaKeyLabelEnum codeKafkaKey,
                               Set<String> groupsToDelivery, UUID entityId, String entityName, String className, String methodName);
}
