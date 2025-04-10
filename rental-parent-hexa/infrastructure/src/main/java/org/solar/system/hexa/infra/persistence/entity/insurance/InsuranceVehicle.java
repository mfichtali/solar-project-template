package org.solar.system.hexa.infra.persistence.entity.insurance;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.annotations.*;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.hexa.infra.persistence.entity.base.AbstractAuditingUuidEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = TableNamesUtils.T_INSURANCE_VEHICLE, schema = TableNamesUtils.RENTAL_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
@FilterDef(name = InsuranceVehicle.CURRENT_INSURANCE_VEHICLE, parameters = @ParamDef(name = InsuranceVehicle.DEF_FILTER_CURRENT_LOCAL_DATE, type = LocalDate.class))
@Filter(name = InsuranceVehicle.CURRENT_INSURANCE_VEHICLE, condition = ":currentLocalDate >= ld_start_insure and :currentLocalDate < ld_end_insure")
public class InsuranceVehicle extends AbstractAuditingUuidEntity {

    public static final String CURRENT_INSURANCE_VEHICLE = "filterInsuranceVehicleOfCurrentLocalDate";
    public static final String DEF_FILTER_CURRENT_LOCAL_DATE = "currentLocalDate";

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "no_insurance_policy", unique = true, nullable = false)
    private String insurancePolicyNumber;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "vehicle_identifier", nullable = false)
    private UUID vehicleIdentifier;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "v_registration_number", nullable = false)
    private String vehicleRegistrationNumber;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "ld_start_insure", nullable = false)
    private LocalDate startInsureDate;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "ld_end_insure", nullable = false)
    private LocalDate endInsureDate;

    @ToString.Include
    @Column(name = "ld_renewal_insure", nullable = false)
    private LocalDate renewalDate;

    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_annual_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal annualInsuranceCost;

    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_annual_cost_with_tax", nullable = false, precision = 10, scale = 2)
    private BigDecimal annualInsuranceCostTtc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insurance_company_id", nullable = false)
    private InsuranceCompany insurer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "insurance_owner_id", nullable = false)
    private InsuranceOwner owner;

    // bi-directional many-to-one association to InsuranceCoverage
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insuranceVehicle")
    private Set<InsuranceCoverage> coverages = new HashSet<>(0);

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insuranceVehicle")
    private Set<InsuranceSinister> insuranceClaims = new HashSet<>(0);

    public InsuranceVehicle attachCoverage(InsuranceCoverage coverage) {

        if (coverage == null) {
            throw new IllegalArgumentException("InsuranceCoverage cannot be null");
        }

        if (this.coverages.contains(coverage)) {
            // Handle duplicate coverage (e.g., throw an exception, update existing coverage)
            throw new IllegalArgumentException("Coverage already exists for this vehicle.");
        }

        this.coverages.add(coverage);
        coverage.setInsuranceVehicle(this);

        return this;
    }

    public void mergeCoverages(Collection<InsuranceCoverage> coverages) {

        if (CollectionUtils.isEmpty(coverages)) {
            return;
        }

        for (InsuranceCoverage newCoverage : coverages) {
            if (!this.coverages.contains(newCoverage)) {
                this.coverages.add(newCoverage);
                newCoverage.setInsuranceVehicle(this);
            } else {
                // Handle duplicate coverage (e.g., throw an exception, update existing coverage)
                throw new IllegalArgumentException("Coverage already exists for this vehicle.");
            }
        }

    }

}
