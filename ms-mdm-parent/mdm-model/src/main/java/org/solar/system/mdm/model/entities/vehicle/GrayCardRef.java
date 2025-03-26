package org.solar.system.mdm.model.entities.vehicle;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.mdm.model.base.AbstractAuditingUuidEntity;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = TableNamesUtils.GRAY_CARD_REF, schema = TableNamesUtils.MDM_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
public class GrayCardRef extends AbstractAuditingUuidEntity {

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private VehicleRef vehicle;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "owner", nullable = false)
    private String owner;

    @ToString.Include
    @Column(name = "address", nullable = false)
    private String address;

    @ToString.Include
    @Column(name = "ref_usage")
    private String usage;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ld_start_circulation_maroc", nullable = false)
    private LocalDate startDateCirculationMaroc;

    @ToString.Include
    @Column(name = "ld_date_end_card_validity", nullable = false)
    private LocalDate dateCardEndValidity;

    @ToString.Include
    @Column(name = "ld_date_operation", nullable = false)
    private LocalDate dateOperation;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "serial_operation_number", nullable = false, unique = true)
    private String serialOperationNumber;

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        if(vehicle != null) {
            vehicleId = vehicle.getId();
        }
    }

}
