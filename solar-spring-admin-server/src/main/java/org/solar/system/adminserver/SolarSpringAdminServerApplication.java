package org.solar.system.adminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableAdminServer
public class SolarSpringAdminServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SolarSpringAdminServerApplication.class, args);
		Environment env = context.getEnvironment();
		ServiceUtils.printRunCompleted(env);
	}

}
