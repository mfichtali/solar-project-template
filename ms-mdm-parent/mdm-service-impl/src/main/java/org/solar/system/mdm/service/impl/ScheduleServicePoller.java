package org.solar.system.mdm.service.impl;

import lombok.RequiredArgsConstructor;
import org.solar.system.central.common.all.exceptions.TechnicalException;
import org.solar.system.central.common.all.messaging.KafkaMessagePayload;
import org.solar.system.mdm.model.entities.vehicle.ScheduleTaskTxOutbox;
import org.solar.system.mdm.model.repository.ScheduleTaskTxOutboxRepository;
import org.solar.system.mdm.service.api.KafkaProducerService;
import org.solar.system.mdm.service.base.AbstractService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleServicePoller extends AbstractService {

    private final KafkaProducerService producer;
    private final ScheduleTaskTxOutboxRepository scheduleTxOutboxRepository;

    @Scheduled(fixedDelay = 10000)
    public void pollActionEventAndPublish() {
        List<ScheduleTaskTxOutbox> unprocessedRecord = scheduleTxOutboxRepository.findByProcessedFalseOrderByCreationDateAsc();
        unprocessedRecord.forEach(record -> {
            try {
                KafkaMessagePayload<String> payload = KafkaMessagePayload.<String> builder()
                        .actionSystem(record.getActionTx()).messageKeyEnum(record.getCodeKafkaKey())
                        .groupsToDelivery(record.getDeliveryGroups())
                        .originClassName(record.getClassInvokeName()).originInvokeMethod(record.getMethodInvokeName())
                        .classOfCast(record.getEntityName()).dataPayload(record.getPayload())
                        .traceId(record.getTraceId()).spanId(record.getSpanId()).build();
                producer.buildAndPublishMonoMessageToMainTopic(payload);
                record.setProcessed(Boolean.TRUE);
            } catch (TechnicalException tex) {
                record.setProcessed(Boolean.FALSE);
                record.setErrorMessage(tex.getMessage());
                record.setClassExceptionName(tex.getCause().getClass().getSimpleName());
            }
            saveOrUpdateVehicleTxOutbox(record);
        });
    }

    private void saveOrUpdateVehicleTxOutbox(ScheduleTaskTxOutbox outbox) {
        scheduleTxOutboxRepository.save(outbox);
    }

}
