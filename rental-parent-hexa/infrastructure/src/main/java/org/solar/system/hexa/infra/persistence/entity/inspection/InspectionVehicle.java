package org.solar.system.hexa.infra.persistence.entity.inspection;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.central.common.vehicle.enums.InspectionResultEnum;
import org.solar.system.hexa.infra.persistence.entity.base.AbstractAuditingUuidEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Vehicle inspection entity.(controle technique)
 */
@Entity
@Table(name = TableNamesUtils.T_INSPECTION_VEHICLE, schema = TableNamesUtils.RENTAL_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
public class InspectionVehicle extends AbstractAuditingUuidEntity {

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "no_inspection", unique = true, nullable = false)
    private String noInspection;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "id_vehicle", nullable = false)
    private UUID idVehicle;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "rn_vehicle", nullable = false)
    private String rnVehicle;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "id_blob_storage", nullable = false)
    private UUID idBlobStorage;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ld_inspection_date", nullable = false)
    private LocalDate inspectionDate;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ld_next_inspection_date")
    private LocalDate nextInspectionDate;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "inspection_mileage", nullable = false)
    private Long inspectionMileage;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "co_inspection_result", nullable = false)
    private InspectionResultEnum inspectionResult;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "inspection_center", nullable = false)
    private String inspectionCenter;

    @Column(name = "inspection_address")
    private String inspectionAddress;

    @Column(name = "inspection_phone")
    private String inspectionPhone;

    @Column(name = "inspection_comment")
    private String inspectionComment;

    @Setter(AccessLevel.NONE)
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_inspection", precision = 10, scale = 2, nullable = false)
    private BigDecimal inspectionCost;

    @Setter(AccessLevel.NONE)
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_purchase_with_vat", precision = 10, scale = 2, nullable = false)
    private BigDecimal inspectionCostWithVAT;

    public void setInspectionCost(@Digits(integer = 8, fraction = 2) BigDecimal inspectionCost) {
        this.inspectionCost = inspectionCost;
        this.inspectionCostWithVAT = ServiceUtils.addPercent(this.inspectionCost, BigDecimal.valueOf(20));
    }
}
