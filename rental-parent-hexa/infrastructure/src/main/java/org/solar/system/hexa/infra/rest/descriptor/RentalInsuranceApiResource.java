package org.solar.system.hexa.infra.rest.descriptor;

import org.solar.system.hexa.domain.records.InsuranceVehicleCreateRequest;

/**
 * /api/insurance-vehicle/[A-Za-z]+-[0-9]+-[A-Za-z0-9]+/apply-coverage
 * /api/insurance-vehicle/create
 * /api/insurance-vehicle/[A-Za-z0-9]+/document-info?type=INSPECTION_CERTIFICATE
 */
public interface RentalInsuranceApiResource {

    default void createInsuranceVehicle(final String language,
                                        final InsuranceVehicleCreateRequest data) {
        //throw new NotImplementedException(TranslatorProvider.getMsg(RENTAL_NOT_YET_IMPLEMENTED));
    }


}
