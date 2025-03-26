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
import java.util.UUID;

@Entity
@Table(name = TableNamesUtils.CALENDAR_BILLING_REF, schema = TableNamesUtils.MDM_DEFAULT_SCHEMA)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
public class CalendarBillingRef extends AbstractAuditingUuidEntity {

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
    @Column(name = "ref_month", nullable = false)
    private String month;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ref_year", nullable = false)
    private String year;

    @Transient
    private String dueDate;

    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_rental", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Setter(AccessLevel.NONE)
    @ToString.Include
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_rental_with_tax", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountIncludingTax;

    public String getDueDate() {
        dueDate = String.format("%s%s", year, month);
        return dueDate;
    }

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
