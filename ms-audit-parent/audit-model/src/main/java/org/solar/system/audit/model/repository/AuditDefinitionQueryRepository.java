package org.solar.system.audit.model.repository;

import org.solar.system.audit.model.entities.AuditDefinitionQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditDefinitionQueryRepository extends JpaRepository<AuditDefinitionQuery, UUID> {
}
