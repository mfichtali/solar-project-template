package org.solar.system.mdm.api.config.global;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.ZonedDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "auditingDateTimeProvider")
public class MdmBeanConfig {

    @Bean
    AuditorAware<String> auditorAware() {
        return new AuditorAwareConfig();
    }

    @Bean
    DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now());
    }

    @Bean
    ObjectMapper objectMapper() {
        JsonMapper jsonMapper = JsonMapper.builder()//
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)//
                .build();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return jsonMapper;
    }

    @Bean
    public QueryStatementInterceptor queryStatementInterceptor() {
        return new QueryStatementInterceptor();
    }

    @Bean
    public HibernatePropertiesCustomizer configureStatementInspector(QueryStatementInterceptor interceptor) {
        return (properties) -> properties.put(AvailableSettings.STATEMENT_INSPECTOR, interceptor);
    }

}
