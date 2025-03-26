package org.solar.system.audit.service.impl;

import lombok.RequiredArgsConstructor;
import org.solar.system.audit.service.api.AuditService;
import org.solar.system.audit.service.api.KafkaConsumerListener;
import org.solar.system.audit.service.api.KafkaProducerService;
import org.solar.system.audit.service.base.AbstractService;
import org.solar.system.central.common.all.enums.KafkaPartitionEnum;
import org.solar.system.central.common.all.messaging.MonoDataKF;
import org.solar.system.central.common.all.pojo.QueryInfoDefinition;
import org.solar.system.central.common.audit.dto.AuditErrorInfo;
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

    private final AuditService auditService;
    private final KafkaProducerService producer;

    @Override
    @KafkaListener(groupId = AUDIT_GROUP_ID,
            containerFactory = FACTORY_JSON_AS_OBJECT,
            topicPartitions = @TopicPartition(topic = MAIN_TOPIC,
                    partitions = {KafkaPartitionEnum.PARTITION_ZERO, KafkaPartitionEnum.PARTITION_ONE}))
    public void receiveMessageDataAsString(@Payload MonoDataKF<String> message,
                                           @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                           @Header(KafkaHeaders.OFFSET) int offset) {

        try {
            proceedHandleAuditStringMessage(message, partition, offset);
        } catch (DeserializationException ex) {
            log.error("Message deserialization error: {}. Partition: {}, Offset: {}", message, partition, offset, ex);
        } catch (Exception ex) {
            log.error("Error consuming message, let's produce in retry ..., Partition: {}, Offset: {}", partition, offset, ex);
            producer.publishToRetryTopic(message, partition, offset, ex);
        }

    }

    @Override
    @KafkaListener(groupId = AUDIT_GROUP_ID,
            containerFactory = RETRY_FACTORY_JSON_AS_OBJECT,
            topicPartitions = @TopicPartition(topic = RETRY_TOPIC,
                    partitions = {KafkaPartitionEnum.PARTITION_ZERO, KafkaPartitionEnum.PARTITION_ONE}))
    public void retryReceiveMessageDataAsString(
            @Payload MonoDataKF<String> message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) int offset) {
        proceedHandleAuditStringMessage(message, partition, offset);
    }

    @Override
    @KafkaListener(groupId = AUDIT_GROUP_ID,
            containerFactory = FACTORY_JSON_AS_OBJECT,
            topicPartitions = @TopicPartition(topic = MAIN_TOPIC, partitions = {KafkaPartitionEnum.PARTITION_THREE}))
    public void receiveQueryDataMessage(
            @Payload MonoDataKF<String> message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) int offset) {

        try {
            final MonoDataKF<QueryInfoDefinition> queryTransformedMessage = message.transformAndMap(() -> convertMonoStringAsObject(message, QueryInfoDefinition.class));
            proceedHandleAuditQueryMessage(queryTransformedMessage, partition, offset);
        } catch (Exception ex) {
            log.error("Error consuming message, lets retry consuming ...", ex);
            producer.publishToRetryTopic(message, partition, offset, ex);
        }

    }

    @Override
    @KafkaListener(groupId = AUDIT_GROUP_ID,
            containerFactory = RETRY_FACTORY_JSON_AS_OBJECT,
            topicPartitions = @TopicPartition(topic = RETRY_TOPIC, partitions = {KafkaPartitionEnum.PARTITION_THREE}))
    public void retryReceiveQueryDataMessage(
            @Payload MonoDataKF<String> message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) int offset) {
        final MonoDataKF<QueryInfoDefinition> queryTransformedMessage = message.transformAndMap(() -> convertMonoStringAsObject(message, QueryInfoDefinition.class));
        proceedHandleAuditQueryMessage(queryTransformedMessage, partition, offset);
    }

    @Override
    @KafkaListener(groupId = AUDIT_GROUP_ID,
            containerFactory = FACTORY_JSON_AS_OBJECT,
            topicPartitions = @TopicPartition(topic = MAIN_TOPIC, partitions = {KafkaPartitionEnum.PARTITION_ELEVEN}))
    public void receiveAuditErrorMessage(
            @Payload MonoDataKF<String> message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) int offset) {

        try {
            final MonoDataKF<AuditErrorInfo> auditErrorTransformedMessage = message.transformAndMap(() -> convertMonoStringAsObject(message, AuditErrorInfo.class));
            proceedHandleAuditErrorMessage(auditErrorTransformedMessage, partition, offset);
        } catch (Exception ex) {
            log.error("Error consuming message, lets retry consuming ...", ex);
            producer.publishToRetryTopic(message, partition, offset, ex);
        }

    }

    @Override
    @KafkaListener(groupId = AUDIT_GROUP_ID,
            containerFactory = RETRY_FACTORY_JSON_AS_OBJECT,
            topicPartitions = @TopicPartition(topic = RETRY_TOPIC, partitions = {KafkaPartitionEnum.PARTITION_ELEVEN}))
    public void retryReceiveAuditErrorMessage(
            @Payload MonoDataKF<String> message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) int offset) {
        final MonoDataKF<AuditErrorInfo> auditErrorTransformedMessage = message.transformAndMap(() -> convertMonoStringAsObject(message, AuditErrorInfo.class));
        proceedHandleAuditErrorMessage(auditErrorTransformedMessage, partition, offset);

    }

    private void proceedHandleAuditErrorMessage(MonoDataKF<AuditErrorInfo> message, int partition, int offset) {
        log.debug("__.*.__.*.__Start Consume Message {} ", writeObjectAsString(message, true));
        message.setPartition(partition);
        message.setOffset(offset);
        auditService.createAuditError(message);
    }

    private void proceedHandleAuditQueryMessage(MonoDataKF<QueryInfoDefinition> message, int partition, int offset) {
        log.info("__.*.__.*.__Start Consume Message {} ", writeObjectAsString(message, true));
        message.setPartition(partition);
        message.setOffset(offset);
        auditService.createAuditQuery(message);
    }

    private void proceedHandleAuditStringMessage(MonoDataKF<String> message, int partition, int offset) {
        log.debug("__.*.__.*.__Start Consume Message {} ", writeObjectAsString(message, true));
        message.setPartition(partition);
        message.setOffset(offset);
        auditService.createAuditTrace(message);
    }
}