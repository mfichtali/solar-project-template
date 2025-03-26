package org.solar.system.mdm.model.entities.vehicle;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.central.common.vehicle.enums.EvenementVehicleTypeEnum;
import org.solar.system.mdm.model.base.AbstractAuditingUuidEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = TableNamesUtils.T_HIST_EVENT_VEHICLE, schema = TableNamesUtils.MDM_DEFAULT_SCHEMA)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
public class HistoricalVehicle extends AbstractAuditingUuidEntity {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "co_event_type", nullable = false)
    private EvenementVehicleTypeEnum eventType;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ts_start_event_h", nullable = false)
    private LocalDateTime startEventDate;

    @ToString.Include
    @Column(name = "ts_end_event_h")
    private LocalDateTime endEventDate;

    @ToString.Include
    @Column(name = "lb_event")
    private String summaryEvent;

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        if(vehicle != null) {
            vehicleId = vehicle.getId();
        }
    }

}
