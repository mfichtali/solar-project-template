package org.solar.system.audit.service.impl;

import lombok.RequiredArgsConstructor;
import org.solar.system.audit.service.api.KafkaProducerService;
import org.solar.system.audit.service.base.AbstractService;
import org.solar.system.central.common.all.messaging.KafkaDomain;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.solar.system.central.common.all.utils.ConstantUtils.RETRY_TOPIC;
import static org.solar.system.central.common.all.utils.ConstantUtils.X_GROUPS_HEADER;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl extends AbstractService implements KafkaProducerService {

    private final KafkaTemplate<String, KafkaDomain> kafkaTemplate;

    @Override
    public void publishToRetryTopic(KafkaDomain origin, Integer partition, Integer offset, Exception ex) {
        /* check kafka producer is enabled **/
        if (!appProperties.isKafkaProducerFlagEnabled()) {
            log.info(">> KAFKA is not enable");
            log.info(">> Message is not processed {}", origin.getMessageKey());
            return;
        }

        /* Proceed send message on retry topic **/
        origin.setErrorMessage(ex.getMessage());
        sendMessage(partition, offset).accept(origin);
    }

    private Consumer<KafkaDomain> sendMessage(final Integer partition, final Integer offset) {
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

                CompletableFuture<SendResult<String, KafkaDomain>> send = kafkaTemplate.send(message);
                send.whenComplete((result, ex) -> {
                    if (ex == null) {
                        int partitionUsed = result.getRecordMetadata().partition();
                        log.info("<<< Message sent on Kafka, topic name [{}], partition [{}]", RETRY_TOPIC, partitionUsed);
                    } else {
                        log.error("Exception occurred when producing message", ex);
                    }
                });

//                ListenableFuture<SendResult<String, KafkaDomain>> send = kafkaTemplate.send(message);
//                int partitionUsed = send.get().getRecordMetadata().partition();
//                log.info("<<< Message sent on Kafka, topic name [{}], partition [{}]", RETRY_TOPIC, partitionUsed);

            } catch (Exception e) {
                log.error("Exception occurred when producing message", e);
            }
        };
    }

}
