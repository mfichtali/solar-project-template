package org.solar.system.audit.service.api;


import org.solar.system.central.common.all.messaging.MonoDataKF;
import org.solar.system.central.common.all.pojo.QueryInfoDefinition;
import org.solar.system.central.common.audit.dto.AuditErrorInfo;

public interface AuditService {

    void createAuditTrace(MonoDataKF<String> message);

    void createAuditQuery(MonoDataKF<QueryInfoDefinition> message);

    void createAuditError(MonoDataKF<AuditErrorInfo> message);

}
