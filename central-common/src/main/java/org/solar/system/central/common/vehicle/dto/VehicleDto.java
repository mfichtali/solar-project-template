package org.solar.system.central.common.vehicle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.solar.system.central.common.vehicle.enums.EnergyTypeEnum;
import org.solar.system.central.common.vehicle.enums.GearboxTypeEnum;
import org.solar.system.central.common.vehicle.enums.VehicleTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class VehicleDto implements Serializable {

    private UUID id;
    private String marque;
    private String model;
    private String finishing;
    private String registrationNumber;
    private EnergyTypeEnum energyType;
    private GearboxTypeEnum gearboxType;
    private VehicleTypeEnum vehicleType;
    private LocalDate startMiseCirculation;
    private Long mileage;
    private String serialChassisNumber;
    private String color;
    private Long numberCylinders;
    private Long numberPlaces;
    private String fiscalPower;
    private String dinPower;
    private BigDecimal amountPurchase;
    private BigDecimal amountPurchaseIncludingTax;
    private String contactPurchaseReference;
    private Boolean inActiveOperational;
    private String commentary;

    private GrayCardDto grayCard;

    private List<CalendarBillingDto> calendarBillings = new ArrayList<>();
    private CalendarBillingDto currentCalendarBilling;

    private List<HistoricalVehicleDto> historicalVehicles = new ArrayList<>();

    private List<CautionBillingDto> cautionBillings = new ArrayList<>();
    private CautionBillingDto currentCautionBilling;

}
