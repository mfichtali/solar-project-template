package org.solar.system.mdm.service.impl;

import lombok.RequiredArgsConstructor;
import org.solar.system.central.common.all.exceptions.TechnicalException;
import org.solar.system.central.common.all.messaging.KafkaDomain;
import org.solar.system.mdm.service.api.KafkaProducerService;
import org.solar.system.mdm.service.base.AbstractKafkaService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.solar.system.central.common.all.enums.GatewayServiceEnum.MDM;
import static org.solar.system.central.common.all.utils.ConstantUtils.*;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl extends AbstractKafkaService implements KafkaProducerService {

    private final KafkaTemplate<String, KafkaDomain> kafkaTemplate;

    @Override
    public String getOriginModule() {
        return MDM.serviceCode;
    }

    @Override
    public String getCurrentTraceId() {
        return currentTraceId();
    }

    @Override
    public String getCurrentSpanId() {
        return currentSpanId();
    }

    @Override
    public void publishToMainTopic(KafkaDomain message) {
        /* check kafka producer is enabled */
        if (!appProperties.isKafkaProducerEnabled()) {
            log.info(">> KAFKA is not enable");
            log.info(">> Message is not processed {}", message.getMessageKey());
            return;
        }
        /* Proceed send message on main topic **/
        sendMessageInMain(message.getPartition()).accept(message);
    }

    @Override
    public void publishToRetryTopic(KafkaDomain origin, Integer partition, Integer offset, Exception ex) {

        /* check kafka producer is enabled **/
        if (!appProperties.isKafkaProducerEnabled()) {
            log.info(">> KAFKA is not enable");
            log.info(">> Message is not processed {}", origin.getMessageKey());
            return;
        }
        /* Proceed send message on retry topic **/
        origin.setErrorMessage(ex.getMessage());
        sendMessageInRetry(partition, offset).accept(origin);

    }

    private Consumer<KafkaDomain> sendMessageInRetry(final Integer partition, final Integer offset) {

        return data -> {
            try {

                log.info(">>> Try Sending message on Kafka, topic name [{}]", RETRY_TOPIC);
                String retryMessageKey = String.format("%s-%s", data.getMessageKey(), "retry");
                Message<KafkaDomain> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, RETRY_TOPIC)
                        .setHeader(KafkaHeaders.KEY, retryMessageKey)
                        .setHeader(KafkaHeaders.PARTITION, partition)
                        .setHeader(KafkaHeaders.OFFSET, offset)
                        .setHeader(X_GROUPS_HEADER, data.getDeliveryGroup())
                        .build();
                publishMessage(message, RETRY_TOPIC);

            } catch (Exception e) {
                log.error("Exception occurred when producing message", e);
            }
        };

    }

    private Consumer<KafkaDomain> sendMessageInMain(final Integer partition) {
        return data -> {
            try {

                log.info(">>> Try Sending message on Kafka, topic name [{}]", MAIN_TOPIC);
                data.setMsCode(getOriginModule());
                Message<KafkaDomain> message;
                if (partition == null) {
                    message = MessageBuilder
                            .withPayload(data)
                            .setHeader(KafkaHeaders.TOPIC, MAIN_TOPIC)
                            .setHeader(KafkaHeaders.KEY, data.getMessageKey())
                            .setHeader(X_GROUPS_HEADER, data.getDeliveryGroup())
                            .build();
                } else {
                    message = MessageBuilder
                            .withPayload(data)
                            .setHeader(KafkaHeaders.TOPIC, MAIN_TOPIC)
                            .setHeader(KafkaHeaders.KEY, data.getMessageKey())
                            .setHeader(KafkaHeaders.PARTITION, data.getPartition())
                            .setHeader(X_GROUPS_HEADER, data.getDeliveryGroup())
                            .build();
                }
                publishMessage(message, MAIN_TOPIC);

            } catch (Exception e) {
                log.error("*<<< Exception occurred when producing message ", e);
                throw new TechnicalException(e.getMessage(), e);
            }
        };
    }

    private void publishMessage(Message<KafkaDomain> message, String mainTopic) {

        CompletableFuture<SendResult<String, KafkaDomain>> send = kafkaTemplate.send(message);
        send.whenComplete((result, ex) -> {
            if (ex == null) {
                int partitionUsed = result.getRecordMetadata().partition();
                log.info("<<< Message sent on Kafka, topic name [{}], partition [{}]", mainTopic, partitionUsed);
            } else {
                log.error("*<<< onFailure - Exception occurred when publish message, topic name [{}]", mainTopic, ex);
                throw new TechnicalException(ex.getMessage(), ex);
            }
        });

//        ListenableFuture<SendResult<String, KafkaDomain>> asyncFutureResponse = kafkaTemplate.send(message);
//        asyncFutureResponse.addCallback(new ListenableFutureCallback<>() {
//
//            @Override
//            public void onSuccess(final SendResult<String, KafkaDomain> result) {
//                int partitionUsed = result.getRecordMetadata().partition();
//                log.info("<<< Message sent on Kafka, topic name [{}], partition [{}]", mainTopic, partitionUsed);
//            }
//
//            @Override
//            public void onFailure(final Throwable ex) {
//                log.error("*<<< onFailure - Exception occurred when publish message, topic name [{}]", mainTopic, ex);
//                throw new TechnicalException(ex.getMessage(), ex);
//            }
//
//        });
    }

}
