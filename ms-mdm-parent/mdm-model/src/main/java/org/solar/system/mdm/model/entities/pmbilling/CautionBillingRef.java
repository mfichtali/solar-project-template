package org.solar.system.mdm.model.entities.pmbilling;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.mdm.model.base.AbstractAuditingUuidEntity;
import org.solar.system.mdm.model.entities.vehicle.VehicleRef;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = TableNamesUtils.CAUTION_BILLING_REF, schema = TableNamesUtils.MDM_DEFAULT_SCHEMA)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
public class CautionBillingRef extends AbstractAuditingUuidEntity {

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private VehicleRef vehicle;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ld_start_caution_h", nullable = false)
    private LocalDate startCautionDate;

    @ToString.Include
    @Column(name = "ld_end_caution_h")
    private LocalDate endCautionDate;

    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_caution", precision = 10, scale = 2)
    private BigDecimal amount;

    @Setter(AccessLevel.NONE)
    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_caution_with_tax", precision = 10, scale = 2)
    private BigDecimal amountIncludingTax;

    public void setAmount(@Digits(integer = 8, fraction = 2) BigDecimal amount) {
        this.amount = amount;
        this.amountIncludingTax = ServiceUtils.addPercent(
                this.amount,
                BigDecimal.valueOf(20));
    }

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        if(vehicle != null) {
            vehicleId = vehicle.getId();
        }
    }
}
