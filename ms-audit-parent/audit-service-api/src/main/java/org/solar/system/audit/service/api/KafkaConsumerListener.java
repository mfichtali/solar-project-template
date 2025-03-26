package org.solar.system.audit.service.api;

import org.solar.system.central.common.all.messaging.MonoDataKF;

public interface KafkaConsumerListener {

    void receiveMessageDataAsString(
            MonoDataKF<String> message,
            int partition,
            int offset);

    void retryReceiveMessageDataAsString(
            MonoDataKF<String> message,
            int partition,
            int offset);

    void receiveQueryDataMessage(
            MonoDataKF<String> message,
            int partition,
            int offset
    );

    void retryReceiveQueryDataMessage(
            MonoDataKF<String> message,
            int partition,
            int offset
    );
    
    void receiveAuditErrorMessage(
            MonoDataKF<String> message,
            int partition,
            int offset
    );

    void retryReceiveAuditErrorMessage(
            MonoDataKF<String> message,
            int partition,
            int offset
    );
    
}
