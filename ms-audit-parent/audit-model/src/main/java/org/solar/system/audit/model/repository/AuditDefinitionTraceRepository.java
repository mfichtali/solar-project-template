package org.solar.system.audit.model.repository;

import org.solar.system.audit.model.entities.AuditDefinitionTrace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditDefinitionTraceRepository extends JpaRepository<AuditDefinitionTrace, UUID> {
}
