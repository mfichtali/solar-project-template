package org.solar.system.audit.service.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@RefreshScope
public class AppLinkerProperties {

    @Value("${application.kafka.producer.enabled:true}")
    private boolean kafkaProducerFlagEnabled;

}
