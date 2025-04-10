package org.solar.system.hexa.infra;

import org.solar.system.central.common.all.utils.ServiceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class RentalApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RentalApplication.class, args);
        Environment env = context.getEnvironment();
        ServiceUtils.printRunCompleted(env);
    }
}
