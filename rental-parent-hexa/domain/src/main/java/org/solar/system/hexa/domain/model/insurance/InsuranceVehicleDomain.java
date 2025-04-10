package org.solar.system.hexa.domain.model.insurance;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class InsuranceVehicleDomain {

    private String insurancePolicyNumber;
    private UUID vehicleIdentifier;
    private String vehicleRegistrationNumber;
    private LocalDate startInsureDate;
    private LocalDate endInsureDate;
    private LocalDate renewalDate;
    private BigDecimal annualInsuranceCost;
    private BigDecimal annualInsuranceCostTtc;
    private InsuranceCompanyDomain insurer;
    private InsuranceOwnerDomain owner;

}
