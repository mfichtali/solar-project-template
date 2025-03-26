package org.solar.system.audit.model.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.solar.system.audit.model.base.AbstractAuditingUuidEntity;
import org.solar.system.central.common.audit.enums.AuditErrorTypeEnum;

import static org.solar.system.central.common.all.utils.TableNamesUtils.AUDIT_DEFAULT_SCHEMA;
import static org.solar.system.central.common.all.utils.TableNamesUtils.T_AUDIT_ERROR_EVENT;

@Entity
@Table(name = T_AUDIT_ERROR_EVENT, schema = AUDIT_DEFAULT_SCHEMA)
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AuditErrorEvent extends AbstractAuditingUuidEntity {

	@Column(name = "co_error_type")
	@Enumerated(EnumType.STRING)
	private AuditErrorTypeEnum errorType;

	private String errorMessage;

	private String classExceptionName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_id", nullable = false)
	private AuditDefinitionError audit;

}
