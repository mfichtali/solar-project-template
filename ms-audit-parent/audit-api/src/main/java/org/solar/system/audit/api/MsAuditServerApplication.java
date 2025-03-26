  package org.solar.system.audit.api;

  import org.solar.system.central.common.all.utils.ServiceUtils;
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.cloud.openfeign.EnableFeignClients;
  import org.springframework.context.ConfigurableApplicationContext;
  import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableFeignClients
public class MsAuditServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MsAuditServerApplication.class, args);
		Environment env = context.getEnvironment();
		ServiceUtils.printRunCompleted(env);
	}

}
