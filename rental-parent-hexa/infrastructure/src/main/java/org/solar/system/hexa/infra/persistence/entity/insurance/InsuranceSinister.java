package org.solar.system.hexa.infra.persistence.entity.insurance;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.central.common.vehicle.enums.SinisterStatusEnum;
import org.solar.system.central.common.vehicle.enums.SinisterTypeEnum;
import org.solar.system.hexa.infra.persistence.entity.base.AbstractAuditingUuidEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = TableNamesUtils.T_INSURANCE_SINISTER, schema = TableNamesUtils.RENTAL_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
public class InsuranceSinister extends AbstractAuditingUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_vehicle_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private InsuranceVehicle insuranceVehicle;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "insurance_vehicle_id", nullable = false)
    private UUID insuranceVehicleId;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "no_insurance_policy", nullable = false)
    private String insurancePolicyNumber;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "ld_sinister_date", nullable = false)
    private LocalDate dateOfSinister;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "vehicle_identifier", nullable = false)
    private UUID vehicleIdentifier;

    @Column(name = "lb_description")
    private String description;

    @Setter(AccessLevel.NONE)
    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_estimated_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedCost;

    @Setter(AccessLevel.NONE)
    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_estimated_cost_with_tax", nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedCostIncludingTax;

    @Setter(AccessLevel.NONE)
    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_real_cost", precision = 10, scale = 2)
    private BigDecimal realCost;

    @Setter(AccessLevel.NONE)
    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_real_cost_with_tax", precision = 10, scale = 2)
    private BigDecimal realCostIncludingTax;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "co_sinister_status", nullable = false)
    private SinisterStatusEnum currentStatus;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "co_sinister_type", nullable = false)
    private SinisterTypeEnum sinisterType;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "lb_customer_identifier", nullable = false)
    private String customerIdentifier;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "lb_customer_name", nullable = false)
    private String customerName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insuranceSinister")
    private Set<InsuranceSinisterEvent> sinisterEvents = new HashSet<>(0);

    // liste document liee au sinistre

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        if (insuranceVehicle != null) {
            insuranceVehicleId = insuranceVehicle.getId();
            insurancePolicyNumber = insuranceVehicle.getInsurancePolicyNumber();
            vehicleIdentifier = insuranceVehicle.getVehicleIdentifier();
        }
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
        this.estimatedCostIncludingTax = ServiceUtils.addPercent(this.estimatedCost, BigDecimal.valueOf(20));
    }

    public void setRealCost(BigDecimal realCost) {
        this.realCost = realCost;
        this.realCostIncludingTax = ServiceUtils.addPercent(this.realCost, BigDecimal.valueOf(20));
    }


    public InsuranceSinister attachSinisterEvent(InsuranceSinisterEvent claimEvent) {
        if (claimEvent == null) {
            throw new IllegalArgumentException("InsuranceSinisterEvent cannot be null");
        }
        this.sinisterEvents.add(claimEvent);
        claimEvent.setInsuranceSinister(this);
        return this;
    }

    public InsuranceSinister mergeCoverages(Collection<InsuranceSinisterEvent> events) {
        if (CollectionUtils.isEmpty(events)) {
            return this;
        }
        events.forEach(newEvent -> {
            this.sinisterEvents.add(newEvent);
            newEvent.setInsuranceSinister(this);
        });
        return this;
    }
}
