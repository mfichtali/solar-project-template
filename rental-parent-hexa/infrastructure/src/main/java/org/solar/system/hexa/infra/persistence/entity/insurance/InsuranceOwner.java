package org.solar.system.hexa.infra.persistence.entity.insurance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.hexa.infra.persistence.entity.base.AbstractAuditingUuidEntity;

@Entity
@Table(name = TableNamesUtils.T_INSURANCE_OWNER, schema = TableNamesUtils.RENTAL_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
public class InsuranceOwner extends AbstractAuditingUuidEntity {

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(nullable = false)
    private String fullName;

    @ToString.Include
    @Column(nullable = false)
    private String address;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(nullable = false)
    private String ownerIdentifier;

}
