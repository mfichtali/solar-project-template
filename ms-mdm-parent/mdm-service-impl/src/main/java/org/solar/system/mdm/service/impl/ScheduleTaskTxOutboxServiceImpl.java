package org.solar.system.mdm.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.solar.system.central.common.all.enums.ActionSystemEnum;
import org.solar.system.central.common.all.enums.KafkaKeyLabelEnum;
import org.solar.system.mdm.model.entities.vehicle.ScheduleTaskTxOutbox;
import org.solar.system.mdm.model.repository.ScheduleTaskTxOutboxRepository;
import org.solar.system.mdm.service.api.ScheduleTaskTxOutboxService;
import org.solar.system.mdm.service.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ScheduleTaskTxOutboxServiceImpl extends AbstractService implements ScheduleTaskTxOutboxService {

    private final ScheduleTaskTxOutboxRepository scheduleTaskTxOutboxRepository;

    @Override
    public void storeScheduleActionTx(Supplier<ScheduleTaskTxOutbox> supplierMapper, KafkaKeyLabelEnum codeKafkaKey,
                                      Set<String> groupsToDelivery, String classInfoFullName, String methodName) {
        ScheduleTaskTxOutbox scheduleTxOutbox = supplierMapper.get();
        scheduleTxOutbox.setCodeKafkaKey(codeKafkaKey);
        scheduleTxOutbox.setGroupsToDelivery(String.join(",", groupsToDelivery));
        scheduleTxOutbox.setClassInvokeName(classInfoFullName);
        scheduleTxOutbox.setMethodInvokeName(methodName);
        Pair<String, String> pairTracing = currentThreadTracing();
        scheduleTxOutbox.setTraceId(pairTracing.getLeft());
        scheduleTxOutbox.setSpanId(pairTracing.getRight());
        scheduleTaskTxOutboxRepository.save(scheduleTxOutbox);
    }

    @Override
    public void storeScheduleActionTx(String payloadContent, ActionSystemEnum actionSystemEnum, KafkaKeyLabelEnum codeKafkaKey,
                                      Set<String> groupsToDelivery, UUID entityId, String entityName, String classInfoFullName, String methodName) {
        ScheduleTaskTxOutbox scheduleTxOutbox = new ScheduleTaskTxOutbox();
        scheduleTxOutbox.setEntityIdentifier(entityId);
        scheduleTxOutbox.setEntityName(entityName);
        scheduleTxOutbox.setPayload(payloadContent);
        scheduleTxOutbox.setActionTx(actionSystemEnum);
        scheduleTxOutbox.setCodeKafkaKey(codeKafkaKey);
        scheduleTxOutbox.setGroupsToDelivery(String.join(",", groupsToDelivery));
        scheduleTxOutbox.setClassInvokeName(classInfoFullName);
        scheduleTxOutbox.setMethodInvokeName(methodName);
        Pair<String, String> pairTracing = currentThreadTracing();
        scheduleTxOutbox.setTraceId(pairTracing.getLeft());
        scheduleTxOutbox.setSpanId(pairTracing.getRight());
        scheduleTaskTxOutboxRepository.save(scheduleTxOutbox);
    }
}
