package org.solar.system.central.common.vehicle.messaging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.solar.system.central.common.vehicle.enums.InspectionResultEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateInspectionVehicleMessage implements Serializable {

    private String noInspection;
    private UUID idVehicle;
    private String rnVehicle;
    private UUID idBlobStorage;
    private LocalDate inspectionDate;
    private LocalDate nextInspectionDate;
    private Long inspectionMileage;
    private InspectionResultEnum inspectionResult;
    private String inspectionCenter;
    private String inspectionAddress;
    private String inspectionPhone;
    private String inspectionComment;
    private BigDecimal inspectionCost;

}
