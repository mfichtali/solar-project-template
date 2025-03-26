package org.solar.system.central.common.all.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DocumentTypeEnum {

    INSPECTION_CERTIFICATE("InspectionVehicle", "VehicleRef"),
    GRAY_CARD("GrayCardRef", "VehicleRef"),
    VIGNETTE("TSAVStickerVehicle", "VehicleRef"),
    INSURANCE_DOC("InsuranceVehicle", "VehicleRef"),
    ;

    public final String objectName;
    public final String objectDomain;
}
