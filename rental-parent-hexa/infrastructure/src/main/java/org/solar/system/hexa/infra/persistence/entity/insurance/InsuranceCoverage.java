package org.solar.system.hexa.infra.persistence.entity.insurance;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.central.common.vehicle.enums.CoverageTypeEnum;
import org.solar.system.hexa.infra.persistence.entity.base.AbstractAuditingUuidEntity;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = TableNamesUtils.T_INSURANCE_COVERAGE, schema = TableNamesUtils.RENTAL_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
public class InsuranceCoverage extends AbstractAuditingUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_vehicle_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private InsuranceVehicle insuranceVehicle;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "insurance_vehicle_id", nullable = false)
    private UUID insuranceVehicleId;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "co_coverage_type", nullable = false)
    private CoverageTypeEnum coverageType;

    @EqualsAndHashCode.Include
    @Column(name = "lb_description")
    private String description;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_coverage_limit", nullable = false, precision = 10, scale = 2)
    private BigDecimal coverageLimit;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_franchise", nullable = false, precision = 10, scale = 2)
    private BigDecimal franchise;

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();

        if (insuranceVehicle != null) {
            insuranceVehicleId = insuranceVehicle.getId();
        }

        if (coverageType != null && StringUtils.isBlank(description)) {
            description = coverageType.getDescription();
        }

    }
}


