package org.solar.system.audit.model.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.solar.system.central.common.all.enums.PartitionQueryTypeEnum;
import org.solar.system.central.common.audit.enums.AuditTypeEnum;

@Entity
@DiscriminatorValue(AuditTypeEnum.Values.QUERY)
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AuditDefinitionQuery extends AbstractAuditDefinition {

    @ToString.Include
    @EqualsAndHashCode.Include
    private String entityName;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String entityIdentifier;

    @Enumerated(EnumType.STRING)
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "co_query_type")
    private PartitionQueryTypeEnum queryType;

    @Column(name = "query_value")
    private String query;

    private String propertyNames;

    private String propertyValues;

    private String propertyChangeValues;
}
