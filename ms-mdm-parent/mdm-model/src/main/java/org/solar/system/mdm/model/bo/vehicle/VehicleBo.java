package org.solar.system.mdm.model.bo.vehicle;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class VehicleBo implements Serializable {

    @NotNull
    @NotEmpty
    private String marque;

    @NotNull
    @NotEmpty
    private String model;
    private String finishing;

    @NotNull
    @NotEmpty
    private String wwRegistrationNumber;

    private LocalDate dateChangeWwRegistration;

    private String registrationNumber;

    @NotNull
    @NotEmpty
    private EnergyTypeEnum energyType;
    @NotNull
    @NotEmpty
    private GearboxTypeEnum gearboxType;
    @NotNull
    @NotEmpty
    private VehicleTypeEnum vehicleType;
    @NotNull
    private LocalDate startMiseCirculation;
    @NotNull
    private Long mileage;
    @NotNull
    @NotEmpty
    private String serialChassisNumber;
    private String color;
    private Long numberCylinders;
    private Long numberPlaces;
    @NotNull
    @NotEmpty
    private String fiscalPower;
    @NotNull
    @NotEmpty
    private String dinPower;

    @NotNull
    private BigDecimal amountPurchase;
    private BigDecimal amountPurchaseIncludingTax;

    @NotNull
    @NotEmpty
    private String contactPurchaseReference;
    private boolean inActiveOperational;
    //private Boolean inOccupation;
    private String commentary;

}
