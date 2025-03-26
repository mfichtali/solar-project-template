package org.solar.system.audit.api.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.solar.system.audit.service.config.properties.KafkaProperties;
import org.solar.system.central.common.all.messaging.KafkaDomain;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final ObjectMapper objectMapper;

    private final KafkaProperties kafkaProperties;

    /**
     * Producer Config
     */

    @Bean
    ProducerFactory<String, KafkaDomain> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());

        // Use custom Partitioner
        configProps.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, KafkaPartitioner.class.getName());
        return new DefaultKafkaProducerFactory<>(configProps,
                new StringSerializer(),
                new JsonSerializer<>(objectMapper));
    }

    @Bean
    KafkaTemplate<String, KafkaDomain> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    @RefreshScope
    AdminClient adminClient() {
        return AdminClient.create(kafkaConfig());
    }

    private Map<String, Object> kafkaConfig() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        configs.put(AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 30000);
        return configs;
    }
}
