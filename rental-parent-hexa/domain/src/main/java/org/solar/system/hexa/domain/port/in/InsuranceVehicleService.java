package org.solar.system.hexa.domain.port.in;


import org.solar.system.hexa.domain.records.InsuranceVehicleCreateRequest;

public interface InsuranceVehicleService {

    void createInsuranceVehicleContract(final InsuranceVehicleCreateRequest insuranceInput);
    
}
