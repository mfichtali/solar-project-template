package org.solar.system.hexa.domain.records;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InsuranceVehicleCreateRequest(
        String registrationNumber,
        LocalDate startInsureDate,
        LocalDate endInsureDate,
        BigDecimal annualInsuranceCost,
        String companyName,
        String companyIdentifier,
        String ownerFullName,
        String ownerIdentifier,
        String ownerAddress
) {

    public InsuranceVehicleCreateRequest {

        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("Registration number cannot be null or blank");
        }

        if (startInsureDate == null) {
            throw new IllegalArgumentException("Start insurance date cannot be null");
        }

        if (endInsureDate == null) {
            throw new IllegalArgumentException("End insurance date cannot be null");
        }

        if (endInsureDate.isBefore(startInsureDate)) {
            throw new IllegalArgumentException("End insurance date must be after start insurance date");
        }

        if (annualInsuranceCost == null || annualInsuranceCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Annual insurance cost must be greater than zero");
        }

        if (companyName == null || companyName.isBlank()) {
            throw new IllegalArgumentException("Company name cannot be null or blank");
        }

        if (companyIdentifier == null || companyIdentifier.isBlank()) {
            throw new IllegalArgumentException("Company identifier cannot be null or blank");
        }

        if (ownerFullName == null || ownerFullName.isBlank()) {
            throw new IllegalArgumentException("Owner full name cannot be null or blank");
        }

        if (ownerIdentifier == null || ownerIdentifier.isBlank()) {
            throw new IllegalArgumentException("Owner identifier cannot be null or blank");
        }

        if (ownerAddress == null || ownerAddress.isBlank()) {
            throw new IllegalArgumentException("Owner address cannot be null or blank");
        }

    }

}
