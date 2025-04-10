package org.solar.system.hexa.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.solar.system.central.common.all.exceptions.BusinessException;
import org.solar.system.central.common.all.exceptions.EntityNotFoundException;
import org.solar.system.central.common.all.utils.DateUtils;
import org.solar.system.central.common.vehicle.containers.PmVehicleContainer;
import org.solar.system.hexa.business.annotation.BusinessService;
import org.solar.system.hexa.business.port.out.RentalGlobalMapperPort;
import org.solar.system.hexa.domain.model.insurance.InsuranceCompanyDomain;
import org.solar.system.hexa.domain.model.insurance.InsuranceVehicleDomain;
import org.solar.system.hexa.domain.port.in.InsuranceVehicleService;
import org.solar.system.hexa.domain.port.out.InsuranceCompanyRepositoryPort;
import org.solar.system.hexa.domain.port.out.InsuranceVehicleRepositoryPort;
import org.solar.system.hexa.domain.port.out.MessageTranslatorProviderPort;
import org.solar.system.hexa.domain.port.out.VehicleReferenceApiPort;
import org.solar.system.hexa.domain.records.InsuranceVehicleCreateRequest;
import org.solar.system.hexa.domain.records.bo.InsuranceOwnerBo;
import org.solar.system.hexa.domain.records.bo.InsuranceVehicleBo;

import static org.solar.system.hexa.domain.constant.BundleConstants.*;

@BusinessService
@Slf4j
@RequiredArgsConstructor
public class InsuranceVehicleBusinessService implements InsuranceVehicleService {

    private final InsuranceVehicleRepositoryPort insuranceVehicleRepository;
    private final InsuranceCompanyRepositoryPort insuranceCompanyRepository;
    private final VehicleReferenceApiPort vehicleReferenceApiClient;
    private final MessageTranslatorProviderPort translatorProvider;
    private final RentalGlobalMapperPort mapperPort;

    @Override
    public void createInsuranceVehicleContract(final InsuranceVehicleCreateRequest request) {

        var rnVehicle = request.registrationNumber();
        var companyName = request.companyName();
        var companyIdentifier = request.companyIdentifier();

        var insuranceVehiclesList = insuranceVehicleRepository.findByRnVehicleDescOnStartDate(rnVehicle);
        var lastIv = insuranceVehiclesList.isEmpty() ? null : insuranceVehiclesList.getFirst();
        assertValidateInsuranceVehicle(request, lastIv);
        var insuranceCompany = insuranceCompanyRepository.findByNameOrIdentifier(companyName, companyIdentifier)
                .orElseThrow(() -> {
                    String errMsg = translatorProvider.translate(RENTAL_COMPANY_ENTITY_NOT_FOUND, companyName);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        var vehicleInfo = vehicleReferenceApiClient.loadVehicleInfoByRegistrationNumber(rnVehicle)
                .orElseThrow(() -> {
                    String errMsg = translatorProvider.translate(RENTAL_REF_VEHICLE_NOT_FOUND, rnVehicle);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        var insuranceVehicleBo = getInsuranceVehicleBo(request, vehicleInfo, insuranceCompany);
        var insuranceVehicle = mapperPort.insuranceVehicleBoToDomain(insuranceVehicleBo, insuranceCompany);
        insuranceVehicleRepository.saveOrUpdateInsuranceVehicle(insuranceVehicle);

    }

    private void assertValidateInsuranceVehicle(final InsuranceVehicleCreateRequest request, final InsuranceVehicleDomain insuranceVehicle) {

        var startInsureDate = request.startInsureDate();
        var endInsureDate = request.endInsureDate();
        if (DateUtils.isEqualOrBefore(endInsureDate, startInsureDate)) {
            var errMsg = translatorProvider.translate(RENTAL_CREATE_INSURANCE_DATE_VALIDATION);
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }

        if (insuranceVehicle != null && startInsureDate.isBefore(insuranceVehicle.getEndInsureDate())) {
            var errMsg = translatorProvider.translate(RENTAL_IV_DATE_ALREADY_COVERED,
                    insuranceVehicle.getVehicleRegistrationNumber(),
                    DateUtils.toStringFormat(insuranceVehicle.getEndInsureDate()));
            log.error(errMsg);
            throw new BusinessException(errMsg);
        }
    }

    private static InsuranceVehicleBo getInsuranceVehicleBo(final InsuranceVehicleCreateRequest request, final PmVehicleContainer.VehicleBasicInfoRecord vehicleInfo, final InsuranceCompanyDomain insuranceCompany) {
        var insuranceOwner = new InsuranceOwnerBo(request.ownerFullName(), request.ownerAddress(), request.ownerIdentifier());
        var insuranceVehicleBo = new InsuranceVehicleBo(
                null,
                vehicleInfo.vehicleId(),
                vehicleInfo.registrationNumber(),
                request.startInsureDate(),
                request.endInsureDate(),
                request.annualInsuranceCost(),
                insuranceOwner
        );
        insuranceVehicleBo = insuranceVehicleBo.withInsurancePolicyNumber(insuranceCompany.getName());
        return insuranceVehicleBo;
    }

}



