package org.solar.system.mdm.api.config.global;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.*;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.solar.system.central.common.all.annotations.HistorizeExcludeProperties;
import org.solar.system.central.common.all.enums.ActionSystemEnum;
import org.solar.system.central.common.all.enums.KafkaKeyLabelEnum;
import org.solar.system.central.common.all.enums.PartitionQueryTypeEnum;
import org.solar.system.central.common.all.pojo.QueryInfoDefinition;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.solar.system.mdm.model.entities.pmbilling.CalendarBillingRef;
import org.solar.system.mdm.model.entities.pmbilling.CautionBillingRef;
import org.solar.system.mdm.model.entities.vehicle.ScheduleTaskTxOutbox;
import org.solar.system.mdm.model.mapper.GlobalMapper;
import org.solar.system.mdm.service.api.ScheduleTaskTxOutboxService;
import org.solar.system.mdm.service.base.AbstractCommon;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.solar.system.central.common.all.messaging.KafkaGroupHeaderEnum.MDM_2_AUDIT;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryPerfInterceptor extends AbstractCommon
        implements PostInsertEventListener, PostUpdateEventListener,
        PostLoadEventListener, PostDeleteEventListener, Serializable {

    private final List<String> EXCLUDE_ENTITIES = List.of(
            ScheduleTaskTxOutbox.class.getSimpleName(),
            CautionBillingRef.class.getSimpleName(),
            CalendarBillingRef.class.getSimpleName());

    private final EntityManagerFactory entityManagerFactory;

    private final GlobalMapper mapper;

    private final ScheduleTaskTxOutboxService scheduleTaskTxOutboxService;

    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(this);
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(this);
    }

//	@Override
//	public boolean requiresPostCommitHanding(EntityPersister persister) {
//		return false;
//	}

    @Override
    public void onPostInsert(PostInsertEvent event) {

//        String traceId = currentTraceId();
//        String spanId = currentSpanId();
        Pair<String, String> pairTracing = currentThreadTracing();
        String entityName = event.getEntity().getClass().getSimpleName();

        if (EXCLUDE_ENTITIES.contains(entityName)) {
            return;
        }

        HistorizeExcludeProperties annotationExcludePropertyEntity = event.getEntity().getClass()
                .getAnnotation(HistorizeExcludeProperties.class);
        String[] excludePropertiesName = Optional.ofNullable(annotationExcludePropertyEntity)
                .map(HistorizeExcludeProperties::exclude).orElse(null);

        String query = QueryContextHolder.getQuery();
        //Serializable identifierEntity = event.getId();
        Object identifierEntity = event.getId();

        final String[] propertyNames = event.getPersister().getPropertyNames();
        final Object[] stateValues = event.getState();

        Map<String, String> mapPropertiesValues = IntStream.range(0, propertyNames.length)
                .boxed()
                .collect(Collectors.toMap(
                        i -> propertyNames[i],
                        i -> Objects.toString(stateValues[i], StringUtils.EMPTY))
                );

        if (ServiceUtils.isNotArrayEmpty(excludePropertiesName)) {
            List<String> excludePropertiesAsList = Arrays.asList(excludePropertiesName);
            excludePropertiesAsList.forEach(mapPropertiesValues::remove);
        }

        List<String> currentStateValues = new ArrayList<>(mapPropertiesValues.values());
        QueryInfoDefinition queryInsertInfo = new QueryInfoDefinition()
                .setEntityName(entityName).setEntityIdentifier(identifierEntity.toString())
                .setQuery(query).setQueryType(PartitionQueryTypeEnum.CREATE)
                .setPropertyNames(String.format("[%s]", String.join(",", mapPropertiesValues.keySet())))
                .setPropertyValues(String.format("[%s]", String.join(",", currentStateValues)));

        scheduleTaskTxOutboxService.storeScheduleActionTx(() -> mapper.queryInfoToTxOutboxMapper(queryInsertInfo, ActionSystemEnum.CREATE, this::writeObjectAsString),
                KafkaKeyLabelEnum.KEY_QUERY_CREATE, Set.of(MDM_2_AUDIT.name()), classInfoFullName(), ServiceUtils.getCurrentMethodName());

        log.info(">>> Transaction (T){}#(S){} started...", pairTracing.getLeft(), pairTracing.getRight());
        log.info("PostInsertEventListener for {}({})", entityName, identifierEntity);
        log.info("Query CREATE ({})", query);
        mapPropertiesValues.forEach((key, value) -> log.info("• PropertyName '{}' value '{}'", key, value));
        log.info("<<< Transaction (T){}#(S){} ended with status 'COMMITTED'", pairTracing.getLeft(), pairTracing.getRight());

    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {

//        String traceId = currentTraceId();
//        String spanId = currentSpanId();
        Pair<String, String> pairTracing = currentThreadTracing();
        final String entityName = event.getEntity().getClass().getSimpleName();

        if (EXCLUDE_ENTITIES.contains(entityName)) {
            return;
        }

        String query = QueryContextHolder.getQuery();
        //Serializable identifierEntity = event.getId();
        Object identifierEntity = event.getId();

        HistorizeExcludeProperties annotationExcludePropertyEntity = event.getEntity().getClass()
                .getAnnotation(HistorizeExcludeProperties.class);
        String[] excludePropertiesName = Optional.ofNullable(annotationExcludePropertyEntity)
                .map(HistorizeExcludeProperties::exclude).orElse(null);

        final int[] dirtyProperties = event.getDirtyProperties();
        final String[] propertyNames = ServiceUtils.getStringElementsAtIndexes(event.getPersister().getPropertyNames(), dirtyProperties);
        final Object[] stateValues = ServiceUtils.getObjectElementsAtIndexes(event.getState(), dirtyProperties);
        final Object[] oldStateValues = ServiceUtils.getObjectElementsAtIndexes(event.getOldState(), dirtyProperties);

        Map<String, Pair<String, String>> mapPropertiesValues = IntStream.range(0, propertyNames.length)
                .boxed()
                .collect(Collectors.toMap(
                        i -> propertyNames[i],
                        i -> Pair.of(Objects.toString(oldStateValues[i], null), Objects.toString(stateValues[i], null))
                ));

        if (ServiceUtils.isNotArrayEmpty(excludePropertiesName)) {
            List<String> excludePropertiesAsList = Arrays.asList(excludePropertiesName);
            excludePropertiesAsList.forEach(mapPropertiesValues::remove);
        }

        Boolean valueDeleted = Boolean.FALSE;
        boolean constraintDeletedProperty = Arrays.asList(propertyNames).contains("deleted");
        if (constraintDeletedProperty) {
            int indexOfDeleted = Arrays.asList(propertyNames).indexOf("deleted");
            valueDeleted = (Boolean) stateValues[indexOfDeleted];
        }

        QueryInfoDefinition queryUpdateInfo = new QueryInfoDefinition()
                .setEntityName(entityName).setEntityIdentifier(identifierEntity.toString())
                .setQuery(query).setQueryType(PartitionQueryTypeEnum.DELETE_LOGIC);

        ActionSystemEnum actionSystem;
        KafkaKeyLabelEnum kafkaKeyLabel = KafkaKeyLabelEnum.KEY_QUERY_DELETE_LOGIC;
        if (!valueDeleted) {
            actionSystem = ActionSystemEnum.UPDATE;
            kafkaKeyLabel = KafkaKeyLabelEnum.KEY_QUERY_UPDATE;
            List<String> prevStateValues = mapPropertiesValues.values().stream().map(Pair::getLeft).collect(Collectors.toList());
            List<String> currentStateValues = mapPropertiesValues.values().stream().map(Pair::getRight).collect(Collectors.toList());
            queryUpdateInfo.setQueryType(PartitionQueryTypeEnum.UPDATE);
            queryUpdateInfo.setPropertyNames(String.format("[%s]", String.join(",", mapPropertiesValues.keySet())));
            queryUpdateInfo.setPropertyValues(String.format("[%s]", String.join(",", prevStateValues)));
            queryUpdateInfo.setPropertyChangeValues(String.format("[%s]", String.join(",", currentStateValues)));
        } else {
            actionSystem = ActionSystemEnum.DELETE;
        }

        scheduleTaskTxOutboxService.storeScheduleActionTx(() -> mapper.queryInfoToTxOutboxMapper(queryUpdateInfo, actionSystem, this::writeObjectAsString),
                kafkaKeyLabel, Set.of(MDM_2_AUDIT.name()), classInfoFullName(), ServiceUtils.getCurrentMethodName());

        log.info(">>> Transaction (T){}#(S){} started...", pairTracing.getLeft(), pairTracing.getRight());
        log.info("{} for {}({})", valueDeleted ? "PostDeleteLogicAction" : "PostUpdateEventListener", entityName,
                identifierEntity);
        log.info("Query UPDATE ({})", query);
        mapPropertiesValues.forEach((key, value) ->
                log.info("• PropertyName '{}' changed from '{}' to '{}'", key,
                        StringUtils.defaultString(value.getLeft(), ""),
                        StringUtils.defaultString(value.getRight(), "")));
        log.info("<<< Transaction (T){}#(S){} ended with status 'COMMITTED'", pairTracing.getLeft(), pairTracing.getRight());

    }

    @Override
    public void onPostLoad(PostLoadEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean requiresPostCommitHandling(final EntityPersister entityPersister) {
        return false;
    }
}
