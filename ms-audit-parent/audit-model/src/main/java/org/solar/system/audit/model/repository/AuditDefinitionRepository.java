package org.solar.system.audit.model.repository;

import org.solar.system.audit.model.entities.AbstractAuditDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditDefinitionRepository extends JpaRepository<AbstractAuditDefinition, UUID> {
}
