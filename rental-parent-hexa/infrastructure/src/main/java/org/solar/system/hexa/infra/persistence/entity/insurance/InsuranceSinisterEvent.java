package org.solar.system.hexa.infra.persistence.entity.insurance;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.central.common.vehicle.enums.SinisterStatusEnum;
import org.solar.system.hexa.infra.persistence.entity.base.AbstractAuditingUuidEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = TableNamesUtils.T_INSURANCE_SINISTER_EVENT, schema = TableNamesUtils.RENTAL_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
public class InsuranceSinisterEvent extends AbstractAuditingUuidEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_sinister_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private InsuranceSinister insuranceSinister;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "insurance_sinister_id", nullable = false)
    private UUID insuranceSinisterId;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "co_sinister_status", nullable = false)
    private SinisterStatusEnum status;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "ld_start_event", nullable = false)
    private LocalDateTime startDate;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "ld_end_event")
    private LocalDateTime endDate;

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        if (insuranceSinister != null) {
            insuranceSinisterId = insuranceSinister.getId();
        }
    }

}
