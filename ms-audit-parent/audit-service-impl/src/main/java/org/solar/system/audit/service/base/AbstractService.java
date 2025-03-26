package org.solar.system.audit.service.base;

import org.solar.system.audit.model.mapper.GlobalMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService extends AbstractCommon {

    public static final String FACTORY_JSON_AS_OBJECT = "kafkaJsonAsObjectListenerContainerFactory";
    public static final String RETRY_FACTORY_JSON_AS_OBJECT = "retryKafkaJsonAsObjectListenerContainerFactory";

    @Autowired
    protected GlobalMapper mapper;

}
