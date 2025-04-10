package org.solar.system.hexa.infra.persistence.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.solar.system.hexa.domain.model.insurance.InsuranceVehicleDomain;
import org.solar.system.hexa.domain.port.out.InsuranceVehicleRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class InsuranceVehicleRepositoryAdapter implements InsuranceVehicleRepositoryPort {

    @Override
    public List<InsuranceVehicleDomain> findByRnVehicleDescOnStartDate(final String registrationNumber) {
        return List.of();
    }

    @Override
    public void saveOrUpdateInsuranceVehicle(final InsuranceVehicleDomain insuranceVehicle) {

    }
}
