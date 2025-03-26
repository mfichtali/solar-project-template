package org.solar.system.audit.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.Accessors;
import org.solar.system.central.common.audit.enums.AuditTypeEnum;

@Entity
@DiscriminatorValue(AuditTypeEnum.Values.ERROR)
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AuditDefinitionError extends AbstractAuditDefinition {

}
