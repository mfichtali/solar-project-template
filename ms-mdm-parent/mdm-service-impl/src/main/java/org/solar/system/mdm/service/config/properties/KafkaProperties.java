package org.solar.system.mdm.service.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.kafka")
@RefreshScope
public class KafkaProperties {

    private boolean enabled;

    private String bootstrapServers;

    private Integer noConcurrency;

    private Integer sessionTimeoutMs;

    private Integer heartbeatIntervalMs;

    private Integer maxPollRecords;

    private Integer maxPollIntervalMs;
}
