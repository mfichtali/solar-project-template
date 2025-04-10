package org.solar.system.hexa.infra.rest.descriptor;

import jakarta.validation.Valid;
import org.solar.system.hexa.domain.records.InsuranceVehicleCreateRequest;
import org.solar.system.hexa.infra.config.base.AbstractResource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.solar.system.central.common.all.utils.HttpConstUtils.HEADER_ACCEPT_LANGUAGE;
import static org.solar.system.central.common.all.utils.HttpConstUtils.HEADER_API_VERSION;
import static org.solar.system.hexa.domain.constant.ConstantUtils.RENTAL_EDP_INSURANCE_CREATE;
import static org.solar.system.hexa.infra.config.base.AbstractResource.RENTAL_INSURANCE_REQUEST_MAPPING;
import static org.solar.system.hexa.infra.config.base.AbstractResource.REST_RENTAL_INSURANCE_RESOURCE;

@Validated
@RestController(REST_RENTAL_INSURANCE_RESOURCE)
@RequestMapping(value = RENTAL_INSURANCE_REQUEST_MAPPING)
//@Tag(name = "Rental Insurance Resource", description = "The rental insurance API")
public class RentalInsuranceDescriptorResource extends AbstractResource implements RentalInsuranceApiResource {

    @PostMapping(RENTAL_EDP_INSURANCE_CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createInsuranceVehicle(
            @RequestHeader(name = HEADER_API_VERSION) final String apiVersion,
            @RequestHeader(name = HEADER_ACCEPT_LANGUAGE, required = false) final String language,
            @Valid @RequestBody final InsuranceVehicleCreateRequest data) {
        //throw new NotImplementedException(TranslatorProvider.getMsg(RENTAL_NOT_YET_IMPLEMENTED));
    }


}
