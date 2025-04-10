package org.solar.system.hexa.domain.records.bo;

import org.solar.system.central.common.all.utils.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InsuranceVehicleBo(
        String insurancePolicyNumber,
        UUID vehicleIdentifier,
        String vehicleRegistrationNumber,
        LocalDate startInsureDate,
        LocalDate endInsureDate,
        BigDecimal annualInsuranceCost,
        InsuranceOwnerBo insuranceOwner
) {

    public InsuranceVehicleBo withInsurancePolicyNumber(String companyName) {
        String insurancePolicyNumber = String.format("%s-%s-%s",
                companyName,
                yearOfInsurance(),
                vehicleRegistrationNumber);
        return new InsuranceVehicleBo(
                insurancePolicyNumber,
                vehicleIdentifier,
                vehicleRegistrationNumber,
                startInsureDate,
                endInsureDate,
                annualInsuranceCost,
                insuranceOwner
        );
    }

    public String yearOfInsurance() {
        int twoDigitStartDate = DateUtils.getTwoLastDigitsYearAsString(startInsureDate());
        int twoDigitEndDate = DateUtils.getTwoLastDigitsYearAsString(endInsureDate());
        if (twoDigitStartDate == twoDigitEndDate) {
            return String.valueOf(twoDigitEndDate);
        }
        return String.format("%d%d", twoDigitStartDate, twoDigitEndDate);
    }
}
