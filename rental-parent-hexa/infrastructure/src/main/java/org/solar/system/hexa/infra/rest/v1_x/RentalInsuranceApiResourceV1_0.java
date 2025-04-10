package org.solar.system.hexa.infra.rest.v1_x;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.solar.system.hexa.domain.port.in.InsuranceVehicleService;
import org.solar.system.hexa.domain.records.InsuranceVehicleCreateRequest;
import org.solar.system.hexa.infra.config.base.AbstractResource;
import org.solar.system.hexa.infra.rest.descriptor.RentalInsuranceApiResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.solar.system.central.common.all.utils.HttpConstUtils.HEADER_ACCEPT_LANGUAGE;
import static org.solar.system.hexa.domain.constant.ConstantUtils.RENTAL_EDP_INSURANCE_CREATE;
import static org.solar.system.hexa.infra.config.base.AbstractResource.RENTAL_INSURANCE_REQUEST_MAPPING_V1_0;
import static org.solar.system.hexa.infra.config.base.AbstractResource.REST_RENTAL_INSURANCE_RESOURCE_V1_0;


@RestController(REST_RENTAL_INSURANCE_RESOURCE_V1_0)
@RequestMapping(value = RENTAL_INSURANCE_REQUEST_MAPPING_V1_0)
@RequiredArgsConstructor
public class RentalInsuranceApiResourceV1_0 extends AbstractResource implements RentalInsuranceApiResource {

    private final InsuranceVehicleService insuranceVehicleService;

    @Override
    @PostMapping(RENTAL_EDP_INSURANCE_CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createInsuranceVehicle(
            @RequestHeader(name = HEADER_ACCEPT_LANGUAGE, required = false) final String language,
            @Valid @RequestBody InsuranceVehicleCreateRequest data) {
        //insuranceVehicleService.createInsuranceContract(data);
    }


}
