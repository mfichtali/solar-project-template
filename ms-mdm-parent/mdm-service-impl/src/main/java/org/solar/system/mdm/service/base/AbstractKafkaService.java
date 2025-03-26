package org.solar.system.mdm.service.base;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public abstract class AbstractKafkaService extends AbstractService {

    @Autowired
    private AdminClient adminClient;

    protected final String MSG_KEY_GLOBAL = "k_global";

    public abstract String getOriginModule();

    protected void verifyExistingTopic(String topicName) throws InterruptedException, ExecutionException {

        Optional.ofNullable(adminClient.listTopics().names().get()).orElse(Collections.emptySet()).stream()
                .filter(topicName::equals).findFirst().orElseThrow(() -> {
                    log.warn("Topic name {} is not present", topicName);
                    return new KafkaException(String.format("Topic name %s is not present", topicName));
                });

    }

//	protected <T> void callBackHandler(Object message, ListenableFuture<SendResult<String, T>> future) {
//
//		future.addCallback(new ListenableFutureCallback<SendResult<String, T>>() {
//
//			@Override
//			public void onSuccess(@SuppressWarnings("null") SendResult<String, T> result) {
//				log.info("Message [{}] delivered with offset {}", message, result.getRecordMetadata().offset());
//			}
//
//			@Override
//			public void onFailure(Throwable ex) {
//				log.warn("Unable to deliver message [{}], ErrMsg : [{}]", message, ex.getMessage());
//			}
//
//		});
//
//	}

}
