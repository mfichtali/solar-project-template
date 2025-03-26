package org.solar.system.central.common.all.messaging;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.solar.system.central.common.all.enums.ActionSystemEnum;

import java.util.Set;
import java.util.function.Function;

public interface KafkaProducerServiceTemplate {

    default void publishToMainTopic(final KafkaDomain message) {
        throw new NotImplementedException("[publishToMainTopic] Default implementation.");
    }

    default void publishToRetryTopic(final KafkaDomain origin, final Integer partition, final Integer offset, Exception ex) {
        throw new NotImplementedException("[publishToRetryTopic] - Default implementation.");
    }

    default <T> void buildAndPublishMonoMessageToMainTopic(KafkaMessagePayload<T> message) {
        final MonoDataKF<T> monoMessage = buildMonoMessage(message.getActionSystem(), message.getGroupsToDelivery(),
                message.getOriginClassName(), message.getOriginInvokeMethod(), message.getClassOfCast(),
                message.getDataPayload(), message.getTraceId(), message.getSpanId());
        if (StringUtils.isNotBlank(message.getMessageKeyString())) {
            monoMessage.buildMessageKey(message.getMessageKeyString(), message.getPartition());
        } else {
            monoMessage.buildMessageKey(message.getMessageKeyEnum());
        }
        publishToMainTopic(monoMessage);
    }

    default <S, T> void buildAndPublishMonoMessageToMainTopic(KafkaMessagePayload<S> message, Function<S, T> transformer) {
        final MonoDataKF<S> monoMessage = buildMonoMessage(message.getActionSystem(), message.getGroupsToDelivery(),
                message.getOriginClassName(), message.getOriginInvokeMethod(), message.getClassOfCast(),
                message.getDataPayload(), message.getTraceId(), message.getSpanId());
        if (transformer != null) {
            MonoDataKF<T> transformedMessage = monoMessage.transformAndMap(() -> transformer.apply(message.getDataPayload()));
            if (StringUtils.isNotBlank(message.getMessageKeyString())) {
                transformedMessage.buildMessageKey(message.getMessageKeyString(), message.getPartition());
            } else {
                transformedMessage.buildMessageKey(message.getMessageKeyEnum());
            }
            publishToMainTopic(transformedMessage);
        } else {
            if (StringUtils.isNotBlank(message.getMessageKeyString())) {
                monoMessage.buildMessageKey(message.getMessageKeyString(), message.getPartition());
            } else {
                monoMessage.buildMessageKey(message.getMessageKeyEnum());
            }
            publishToMainTopic(monoMessage);
        }
    }

    private <T> MonoDataKF<T> buildMonoMessage(ActionSystemEnum action, Set<String> groupsToDelivery,
            String originClassName, String originInvokeMethod, String classOfCast, T dataMessage, String traceId, String spanId) {
        return MonoDataKF.<T> builder()
                .action(action).groupsToDelivery(groupsToDelivery)
                .className(originClassName).methodName(originInvokeMethod).classOfCast(classOfCast)
                .traceId(StringUtils.isBlank(traceId) ? getCurrentTraceId() : traceId)
                .spanId(StringUtils.isBlank(spanId) ? getCurrentSpanId() : spanId)
                .data(dataMessage)
                .build();
    }

    default String getCurrentTraceId() {
        return null;
    }

    default String getCurrentSpanId() {
        return null;
    }

}