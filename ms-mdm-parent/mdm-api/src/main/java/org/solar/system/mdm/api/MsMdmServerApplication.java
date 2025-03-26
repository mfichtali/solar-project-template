package org.solar.system.mdm.api;

import lombok.extern.slf4j.Slf4j;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
public class MsMdmServerApplication {

	//private static final String APPLICATION_KAFKA_PRODUCER_ENABLED = "application.kafka.producer.enabled";

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(MsMdmServerApplication.class, args);

		Environment env = context.getEnvironment();

		ServiceUtils.printRunCompleted(env);

//		produceMsgAfterLaunchDone(() -> //
//			Boolean.valueOf(env.getProperty(APPLICATION_KAFKA_PRODUCER_ENABLED)), //
//			context);

	}

//	private static void produceMsgAfterLaunchDone(Supplier<Boolean> condition, ConfigurableApplicationContext context) {
//
//		if(condition.get()) {
//			context.getBean(KafkaProducerService.class) //
//					.notifyChangeToAuth(null, null, ServiceUtils.getCurrentMethodName());
//		} else {
//			log.info(">> Kafka Producer message are not enable");
//		}
//
//	}

}
