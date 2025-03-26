package org.solar.system.mdm.service.impl;

import lombok.RequiredArgsConstructor;
import org.solar.system.central.common.all.enums.KafkaPartitionEnum;
import org.solar.system.central.common.all.messaging.MonoDataKF;
import org.solar.system.central.common.vehicle.messaging.CreateHistoricalVehicleMessage;
import org.solar.system.mdm.service.api.KafkaConsumerListener;
import org.solar.system.mdm.service.api.KafkaProducerService;
import org.solar.system.mdm.service.api.VehicleService;
import org.solar.system.mdm.service.base.AbstractService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static org.solar.system.central.common.all.utils.ConstantUtils.*;

@Service
@RequiredArgsConstructor
public class KafkaConsumerListenerImpl extends AbstractService implements KafkaConsumerListener {

    private final KafkaProducerService producerService;

    private final VehicleService vehicleService;

    @Override
    @KafkaListener(groupId = MDM_GROUP_ID,
            containerFactory = FACTORY_JSON_AS_OBJECT,
            topicPartitions = @TopicPartition(topic = MAIN_TOPIC, partitions = {KafkaPartitionEnum.PARTITION_ONE}))
    public void receiveHistorizeVehicleMessage(@Payload MonoDataKF<String> originMessage,
                                               @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                                               @Header(KafkaHeaders.OFFSET) Integer offset) {

        try {
            final MonoDataKF<CreateHistoricalVehicleMessage> transformedMessage = originMessage.transformAndMap(() -> convertMonoStringAsObject(originMessage, CreateHistoricalVehicleMessage.class));
            proceedHandleHistoricalVehicleMessage(transformedMessage, partition, offset);
        } catch (DeserializationException ex) {
            log.error("Message deserialization error: {}. Partition: {}, Offset: {}", originMessage, partition, offset, ex);
        } catch (Exception ex) {
            log.error("Error consuming originMessage, let's produce in retry ..., Partition: {}, Offset: {}", partition, offset, ex);
            producerService.publishToRetryTopic(originMessage, partition, offset, ex);
        }
    }

    @Override
    @KafkaListener(groupId = MDM_GROUP_ID,
            containerFactory = RETRY_FACTORY_JSON_AS_OBJECT,
            topicPartitions = @TopicPartition(topic = RETRY_TOPIC,
                    partitions = {KafkaPartitionEnum.PARTITION_ONE}))
    public void retryReceiveHistorizeVehicleMessage(@Payload MonoDataKF<String> originMessage,
                                                    @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                                                    @Header(KafkaHeaders.OFFSET) Integer offset) {

        try {
            final MonoDataKF<CreateHistoricalVehicleMessage> transformedMessage = originMessage.transformAndMap(() -> convertMonoStringAsObject(originMessage, CreateHistoricalVehicleMessage.class));
            proceedHandleHistoricalVehicleMessage(transformedMessage, partition, offset);
        } catch (DeserializationException ex) {
            log.error("Message deserialization error: {}. Partition: {}, Offset: {}", originMessage, partition, offset, ex);
        }
    }

    private void proceedHandleHistoricalVehicleMessage(MonoDataKF<CreateHistoricalVehicleMessage> message, Integer partition, Integer offset) {
        log.info("__.*.__.*.__Start Consume Message {} ", writeObjectAsString(message, true));
        message.setPartition(partition);
        message.setOffset(offset);
        vehicleService.createHistoricalEventVehicle(message);
    }

}