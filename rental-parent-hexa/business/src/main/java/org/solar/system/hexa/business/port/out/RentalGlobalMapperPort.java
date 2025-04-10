package org.solar.system.hexa.business.port.out;

import org.solar.system.hexa.domain.model.insurance.InsuranceCompanyDomain;
import org.solar.system.hexa.domain.model.insurance.InsuranceVehicleDomain;
import org.solar.system.hexa.domain.records.bo.InsuranceVehicleBo;

public interface RentalGlobalMapperPort {

    InsuranceVehicleDomain insuranceVehicleBoToDomain(final InsuranceVehicleBo bo, final InsuranceCompanyDomain insuranceCompany);

}
