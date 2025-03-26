package org.solar.system.audit.api.config.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class LoadBalancerConfig {

	@Bean
	ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
			ConfigurableApplicationContext context) {
		log.info("Configuring Load balancer to prefer same instance");
		return ServiceInstanceListSupplier.builder().withBlockingDiscoveryClient().withSameInstancePreference()
				.build(context);
	}

}
