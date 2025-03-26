package org.solar.system.audit.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.solar.system.audit.model.base.AbstractAuditingUuidEntity;
import org.solar.system.central.common.audit.enums.AuditTypeEnum;

@MappedSuperclass
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class AbstractAuditDiscriminator extends AbstractAuditingUuidEntity {

    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    @Column(name = "co_audit_type", nullable = false, insertable = false, updatable = false, length = 5)
    private AuditTypeEnum auditType;
}
