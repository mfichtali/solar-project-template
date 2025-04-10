package org.solar.system.hexa.infra.config.global;

import org.solar.system.hexa.business.annotation.BusinessService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.solar.system.hexa.infra.config.global.ComponentScanConfig.*;

@Configuration
@EnableDiscoveryClient
@EntityScan(ENTITY_SCAN)
@EnableFeignClients(basePackages = {FEIGN_SCAN})
@EnableJpaRepositories(REPOSITORY_SCAN_PACKAGE)
@ComponentScan(
        basePackages = {API_REST_SCAN, PERSISTENCE_ADAPTER_SCAN,
                DOMAIN_PORT_IN_SCAN, DOMAIN_PORT_OUT_SCAN, TRANSLATOR_SERVICE_SCAN,
                BUSINESS_PORT_OUT_SCAN, BUSINESS_SERVICE_SCAN, MAPPER_ADAPTER_SCAN},
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = BusinessService.class))
public class ComponentScanConfig {

    public static final String FEIGN_SCAN = "org.solar.system.hexa.infra.api.feign";
    public static final String ENTITY_SCAN = "org.solar.system.hexa.infra.persistence.entity";
    public static final String REPOSITORY_SCAN_PACKAGE = "org.solar.system.hexa.infra.persistence.repository";

    public static final String API_REST_SCAN = "org.solar.system.hexa.infra.rest";

    public static final String MAPPER_ADAPTER_SCAN = "org.solar.system.hexa.infra.mapper";

    public static final String PERSISTENCE_ADAPTER_SCAN = "org.solar.system.hexa.infra.persistence.adapter";

    public static final String DOMAIN_PORT_IN_SCAN = "org.solar.system.hexa.domain.port.in";
    public static final String DOMAIN_PORT_OUT_SCAN = "org.solar.system.hexa.domain.port.out";

    public static final String BUSINESS_PORT_OUT_SCAN = "org.solar.system.hexa.business.port.out";
    public static final String BUSINESS_SERVICE_SCAN = "org.solar.system.hexa.business.service";

    public static final String TRANSLATOR_SERVICE_SCAN = "org.solar.system.hexa.infra.translator";

}
