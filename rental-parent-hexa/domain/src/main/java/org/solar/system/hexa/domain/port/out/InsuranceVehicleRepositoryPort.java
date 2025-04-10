package org.solar.system.hexa.domain.port.out;

import org.solar.system.hexa.domain.model.insurance.InsuranceVehicleDomain;

import java.util.List;

public interface InsuranceVehicleRepositoryPort {

    List<InsuranceVehicleDomain> findByRnVehicleDescOnStartDate(String registrationNumber);

    void saveOrUpdateInsuranceVehicle(InsuranceVehicleDomain insuranceVehicle);
}
