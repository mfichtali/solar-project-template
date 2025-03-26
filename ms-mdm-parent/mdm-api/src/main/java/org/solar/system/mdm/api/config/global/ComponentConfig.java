package org.solar.system.mdm.api.config.global;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.solar.system.mdm.api.config.global.ComponentConfig.*;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EntityScan(ENTITY_SCAN)
@EnableJpaRepositories(REPOSITORY_SCAN_PACKAGE)
@ComponentScan({ API_SCAN, SERVICE_SCAN, MAPPER_SCAN })
public class ComponentConfig {
	
	public static final String ENTITY_SCAN 				= "org.solar.system.mdm.model.entities";
	public static final String REPOSITORY_SCAN_PACKAGE 	= "org.solar.system.mdm.model.repository";
	public static final String API_SCAN 				= "org.solar.system.mdm.api";
	public static final String SERVICE_SCAN 			= "org.solar.system.mdm.service";
	public static final String MAPPER_SCAN 				= "org.solar.system.mdm.model.mapper";
	
}
