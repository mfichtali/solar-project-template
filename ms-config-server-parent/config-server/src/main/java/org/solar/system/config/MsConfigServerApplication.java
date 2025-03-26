package org.solar.system.config;

import org.solar.system.central.common.all.utils.ServiceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class MsConfigServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MsConfigServerApplication.class, args);
        Environment env = context.getEnvironment();
        ServiceUtils.printRunCompleted(env);
    }

}
