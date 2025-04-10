package org.solar.system.hexa.infra.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.solar.system.hexa.domain.model.insurance.InsuranceCompanyDomain;
import org.solar.system.hexa.domain.model.insurance.InsuranceVehicleDomain;
import org.solar.system.hexa.domain.records.bo.InsuranceVehicleBo;
import org.solar.system.hexa.infra.persistence.entity.insurance.InsuranceCompany;

@Mapper(componentModel = "spring")
public interface GlobalMapper {

    GlobalMapper INSTANCE = Mappers.getMapper(GlobalMapper.class);

    InsuranceCompanyDomain insuranceCompanyToDomain(InsuranceCompany input);

    InsuranceCompany insuranceCompanyToEntity(InsuranceCompanyDomain input);

    InsuranceVehicleDomain insuranceVehicleBoToDomain(InsuranceVehicleBo bo, @Context InsuranceCompanyDomain insuranceCompany);

    @AfterMapping
    default void postMappingInsuranceVehicleDomain(@MappingTarget InsuranceVehicleDomain insuranceVehicleDomain,
                                                   @Context InsuranceCompanyDomain insuranceCompany) {
        insuranceVehicleDomain.setInsurer(insuranceCompany);
    }

}
