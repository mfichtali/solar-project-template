package org.solar.system.mdm.api.rest;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.solar.system.central.common.all.config.AppHealthyChecker;
import org.solar.system.central.common.all.enums.ActionSystemEnum;
import org.solar.system.central.common.all.messaging.KafkaGroupHeaderEnum;
import org.solar.system.central.common.all.messaging.KafkaMessagePayload;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.solar.system.mdm.service.api.KafkaProducerService;
import org.solar.system.mdm.service.config.CacheComponentUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.solar.system.central.common.all.messaging.KafkaGroupHeaderEnum.MDM_2_AUDIT;
import static org.solar.system.central.common.all.utils.ConstantUtils.*;

@RestController
@RequestMapping(value = "${application.endpoint-api-key}/admin")
@RequiredArgsConstructor
public class AdminMdmResource extends AbstractResource {

    private final AdminClient adminClient;
    private final KafkaProducerService producer;
    private final CacheComponentUtils cacheComponentUtils;

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public AppHealthyChecker healthyChecker() {

        log.info("Call check service MDM");
        return AppHealthyChecker.builder()
                .msStatus(MS_CHECKER_UP)
                .kafkaStatus(verifyKafkaConnection() ? MS_KAFKA_UP : MS_KAFKA_DOWN)
                .topicsInformation(topicsNameList())
                .build();

    }

    @PostMapping("/produce/{msgKey}/{groupHeader}/{message}")
    public ResponseEntity<Void> produceMsgOnTopicMain(@PathVariable String msgKey,
                                                      @PathVariable String groupHeader,
                                                      @PathVariable String message) {

        KafkaGroupHeaderEnum group = KafkaGroupHeaderEnum.fromString(groupHeader);
        if (group == null) {
            return ResponseEntity.badRequest().build();
        }
        Pair<String, String> pairTracing = currentThreadTracing();

        KafkaMessagePayload<String> payload = KafkaMessagePayload.<String>builder()
                .actionSystem(ActionSystemEnum.FAKE).messageKeyString(msgKey)
                .groupsToDelivery(Set.of(MDM_2_AUDIT.name()))
                .originClassName(classInfoFullName()).originInvokeMethod(ServiceUtils.getCurrentMethodName())
                .classOfCast("String").dataPayload(message)
                .traceId(pairTracing.getLeft()).spanId(pairTracing.getRight()).build();
        producer.buildAndPublishMonoMessageToMainTopic(payload);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/cache/free")
    @ResponseStatus(HttpStatus.OK)
    public void freeMdmCache(@RequestParam(required = false, name = "cacheName") Optional<String> cacheName) {

        log.info("*** Free of cache {} ***", cacheName.orElse(StringUtils.EMPTY));
        cacheName.filter(StringUtils::isNotBlank)
                .map(cacheComponentUtils::evictCacheByName)
                .orElseGet(cacheComponentUtils::evictAllCaches);

    }

    @GetMapping("/cache/list")
    @ResponseStatus(HttpStatus.OK)
    public List<String> cacheNames() {

        log.info("*** List of cache names ***");
        return cacheComponentUtils.cacheNames();

    }

    /*************************************/
    /**         Private method 		**/
    /*************************************/

    private boolean verifyKafkaConnection() {
        Collection<Node> nodes = null;
        try {
            nodes = adminClient.describeCluster().nodes().get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Connection to Kafka is failed ...");
            Thread.currentThread().interrupt();
        }
        return CollectionUtils.isNotEmpty(nodes);
    }

    private Map<String, String> topicsNameList() {
        Map<String, String> topicsInformation = null;
        try {
            Set<String> topics = adminClient.listTopics().names().get();
            DescribeTopicsResult describeTopics = adminClient.describeTopics(topics);
            Map<String, KafkaFuture<TopicDescription>> infoTopicsValue = describeTopics.values();

            topicsInformation = infoTopicsValue.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, value -> {
                        try {
                            return String.format("Topic identifier [%s], Nbr Of Partition(s) [%d]", //
                                    value.getValue().get().topicId().toString(),
                                    value.getValue().get().partitions().size());
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                        return null;
                    }));

        } catch (InterruptedException | ExecutionException e) {
            log.error("Connection to Kafka is failed ...");
            Thread.currentThread().interrupt();
        }
        return topicsInformation;
    }

}
