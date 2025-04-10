package org.solar.system.hexa.infra.mapper.adapter;

import lombok.RequiredArgsConstructor;
import org.solar.system.hexa.business.port.out.RentalGlobalMapperPort;
import org.solar.system.hexa.domain.model.insurance.InsuranceCompanyDomain;
import org.solar.system.hexa.domain.model.insurance.InsuranceVehicleDomain;
import org.solar.system.hexa.domain.records.bo.InsuranceVehicleBo;
import org.solar.system.hexa.infra.mapper.GlobalMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentalGlobalMapperAdapter implements RentalGlobalMapperPort {

    private final GlobalMapper mapper;

    @Override
    public InsuranceVehicleDomain insuranceVehicleBoToDomain(final InsuranceVehicleBo bo, final InsuranceCompanyDomain insuranceCompany) {
        return mapper.insuranceVehicleBoToDomain(bo, insuranceCompany);
    }
}
