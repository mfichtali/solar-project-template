package org.solar.system.audit.service.impl;

import lombok.RequiredArgsConstructor;
import org.solar.system.audit.model.entities.AuditDefinitionError;
import org.solar.system.audit.model.entities.AuditDefinitionQuery;
import org.solar.system.audit.model.entities.AuditDefinitionTrace;
import org.solar.system.audit.model.entities.AuditErrorEvent;
import org.solar.system.audit.model.repository.AuditDefinitionRepository;
import org.solar.system.audit.model.repository.AuditErrorEventRepository;
import org.solar.system.audit.service.api.AuditService;
import org.solar.system.audit.service.base.AbstractService;
import org.solar.system.central.common.all.messaging.MonoDataKF;
import org.solar.system.central.common.all.pojo.QueryInfoDefinition;
import org.solar.system.central.common.audit.dto.AuditErrorInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class AuditServiceImpl extends AbstractService implements AuditService {

    private final AuditDefinitionRepository auditDefinitionRepository;
    private final AuditErrorEventRepository auditErrorEventRepository;

    @Override
    public void createAuditTrace(MonoDataKF<String> message) {
        AuditDefinitionTrace auditTraceEntity = mapper.messageToAuditTraceEntity(message);
        auditDefinitionRepository.save(auditTraceEntity);
    }

    @Override
    public void createAuditQuery(MonoDataKF<QueryInfoDefinition> message) {
        AuditDefinitionQuery auditQueryEntity = mapper.messageToAuditQueryEntity(message);
        auditDefinitionRepository.save(auditQueryEntity);
    }

    @Override
    public void createAuditError(MonoDataKF<AuditErrorInfo> message) {
        AuditDefinitionError auditErrorEntity = mapper.messageToAuditErrorEntity(message);
        AuditErrorEvent auditErrorEventEntity = mapper.toAuditErrorEventEntity(message.getData());
        auditDefinitionRepository.save(auditErrorEntity);
        auditDefinitionRepository.flush();
        auditErrorEventEntity.setAudit(auditErrorEntity);
        auditErrorEventRepository.save(auditErrorEventEntity);
    }

}
