package org.solar.system.audit.model.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.solar.system.central.common.all.enums.ActionSystemEnum;

import java.time.LocalDateTime;

import static org.solar.system.central.common.all.utils.TableNamesUtils.AUDIT_DEFAULT_SCHEMA;
import static org.solar.system.central.common.all.utils.TableNamesUtils.T_AUDIT_EVENT;

@Entity
@Table(name = T_AUDIT_EVENT, schema = AUDIT_DEFAULT_SCHEMA)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="co_audit_type",
        discriminatorType = DiscriminatorType.STRING,
        columnDefinition = "VARCHAR(5)")
@Getter
@Setter
@Accessors(chain = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class AbstractAuditDefinition extends AbstractAuditDiscriminator {

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "co_module", nullable = false)
    private String moduleCode;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "sys_action", nullable = false)
    private ActionSystemEnum action;

    @Column(name = "lb_audit_creation", nullable = false)
    private LocalDateTime dateCreation;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "group_header", nullable = false)
    private String groupHeader;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "message_key", nullable = false)
    private String messageKey;

    @Column(name = "no_partition", nullable = false)
    private Integer partition;

    @Column(name = "no_offset", nullable = false)
    private Integer offset;

    @Column(nullable = false)
    private String classOfMessage;

    @Column(nullable = false)
    private String className;

    @Column(nullable = false)
    private String methodName;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String traceId;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String spanId;

}
