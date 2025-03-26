package org.solar.system.mdm.service.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@Getter
@RefreshScope
public class AppLinkerProperties {

	@Value("${application.kafka.producer.enabled}")
	private boolean kafkaProducerEnabled;

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${application.historize.perform.enabled}")
	private boolean historizePerformEnabled;

}
