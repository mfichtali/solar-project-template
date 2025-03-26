package org.solar.system.audit.api.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.solar.system.audit.service.config.properties.KafkaProperties;
import org.solar.system.central.common.all.messaging.KafkaDomain;
import org.solar.system.central.common.all.messaging.KafkaGroupHeaderEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.solar.system.central.common.all.utils.ConstantUtils.*;

@Slf4j
@Configuration
//@ConditionalOnBean(KafkaProducerConfig.class)
@RequiredArgsConstructor
public class KafkaConsumerConfig {

	private final KafkaProperties kafkaProperties;

	private final ObjectMapper objectMapper;

	@Bean
	Map<String, Object> consumerConfigs() {

		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, AUDIT_GROUP_ID);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

 		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaProperties.getMaxPollRecords());
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, kafkaProperties.getMaxPollIntervalMs());
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaProperties.getSessionTimeoutMs());
		props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, kafkaProperties.getHeartbeatIntervalMs());

		return props;

	}

	@Bean
	ConsumerFactory<String, KafkaDomain> consumerJsonToObjectFactory() {

		final String[] trustedPackages = {"org.solar.system.central.common.all.messaging"};
		final JsonDeserializer<KafkaDomain> jsonDeserializer = new JsonDeserializer<>(objectMapper);
		jsonDeserializer.addTrustedPackages(trustedPackages);
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(), 
				new StringDeserializer(), 
				jsonDeserializer);

	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, KafkaDomain> kafkaJsonAsObjectListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, KafkaDomain> factory =
				new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(consumerJsonToObjectFactory());

		// Configuration de la concurrence
		factory.setConcurrency(kafkaProperties.getNoConcurrency());

		factory.setBatchListener(true);

		// Délai d'attente pour récupérer les messages (en ms)
		//factory.getContainerProperties().setPollTimeout(5000);

		// Apply filter
		factory.setRecordFilterStrategy(applyFilter());

		return factory;

	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, KafkaDomain> retryKafkaJsonAsObjectListenerContainerFactory(
			KafkaTemplate<String, KafkaDomain> kafkaTemplate
	) {

		final ConcurrentKafkaListenerContainerFactory<String, KafkaDomain> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerJsonToObjectFactory());
		factory.setCommonErrorHandler(errorHandler(kafkaTemplate));

		//factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
		//factory.setAckDiscarded(true);
		factory.setRecordFilterStrategy(applyFilter());
//		factory.afterPropertiesSet();
		return factory;

	}

	@Bean
	DefaultErrorHandler errorHandler(KafkaTemplate<String, KafkaDomain> kafkaTemplate) {
		BackOff fixedBackOff = new FixedBackOff(3000L, 3L);
		DefaultErrorHandler errorHandler =  new DefaultErrorHandler((record, e) -> {

			log.info("Push to DLT topic");

			KafkaDomain kafkaDomain = (KafkaDomain) record.value();
			kafkaTemplate.send(
					DLT_TOPIC,
					kafkaDomain.getPartition(),
					String.format("%s-%s", kafkaDomain.getMessageKey(), "dlt"),
					kafkaDomain
			);

		}, fixedBackOff);

		errorHandler.addRetryableExceptions(
				SocketTimeoutException.class);
		return errorHandler;
	}

	@Bean
	RecordFilterStrategy<String, KafkaDomain> applyFilter() {
		return record -> {
			Header xGroupsHeader = record.headers().lastHeader(X_GROUPS_HEADER);
			String groupsHeaderAsString = new String(xGroupsHeader.value(), StandardCharsets.UTF_8);
			return isAuditGroupMatch(groupsHeaderAsString);
		};
	}

	private static boolean isAuditGroupMatch(String groupsHeaderAsString) {
		boolean groupMatch = true;
		if(StringUtils.isNotBlank(groupsHeaderAsString)) {
			if(groupsHeaderAsString.contains(",")) {
				groupsHeaderAsString = KafkaGroupHeaderEnum.filterOfAudit(groupsHeaderAsString.split(","));
			}
			if (groupsHeaderAsString != null) {
				groupMatch = KafkaGroupHeaderEnum.groupAudit().stream().noneMatch(groupsHeaderAsString::equals);
			}
		}
		return groupMatch;
	}

}
