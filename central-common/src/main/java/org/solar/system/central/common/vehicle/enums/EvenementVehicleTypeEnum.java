package org.solar.system.central.common.vehicle.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EvenementVehicleTypeEnum {

    ICV("ICV", "Initialization Creation Vehicle"),
    PIC("PIC", "Putting Into Circulation"),
    AIV("AIV", "Accident Incident Vehicle"),
    URM("URM", "Under Repair Maintenance"),
    CRP("CRP", "Change Registration Plate"),
    DCB("DCB", "Define new calendar billing"),
    CCB("DCB", "Copy existing calendar billing"),
    DCT("DCT", "Define new caution billing"),
    IVC("IVC", "Vehicle coverage applied"),

    ;

    private final String eventCode;
    private final String eventDescription;

}
