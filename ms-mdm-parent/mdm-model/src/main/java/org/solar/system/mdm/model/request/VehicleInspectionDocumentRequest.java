package org.solar.system.mdm.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.solar.system.central.common.vehicle.enums.InspectionResultEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class VehicleInspectionDocumentRequest extends VehicleDocumentRequest implements Serializable {
    
    private String noInspection;
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
