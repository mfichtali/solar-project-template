package org.solar.system.mdm.service.api;

import org.solar.system.central.common.all.messaging.MonoDataKF;

public interface KafkaConsumerListener {

    void receiveHistorizeVehicleMessage(
            final MonoDataKF<String> message,
            final Integer partition,
            final Integer offset);

    void retryReceiveHistorizeVehicleMessage(
            final MonoDataKF<String> message,
            final Integer partition,
            final Integer offset);

}
