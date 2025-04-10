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
@Table(name = TableNamesUtils.T_INSURANCE_COMPANY, schema = TableNamesUtils.RENTAL_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
public class InsuranceCompany extends AbstractAuditingUuidEntity {

    @Column(name = "company_name", nullable = false, unique = true)
    private String name;

    @Column(name = "company_ref", nullable = false, unique = true)
    private String companyIdentifier;

    private String address;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

}
