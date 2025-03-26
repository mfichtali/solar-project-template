package org.solar.system.registry;

import org.solar.system.central.common.all.utils.ServiceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaServer
public class MsServiceRegistryApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MsServiceRegistryApplication.class, args);
		Environment env = context.getEnvironment();
		ServiceUtils.printRunCompleted(env);
	}

}
