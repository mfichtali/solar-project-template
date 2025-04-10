package org.solar.system.hexa.domain.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantUtils {

    public static final String RENTAL_API_URL_REGEX = "^(/api/v[0-9]+\\\\.[0-9]+/[A-Za-z0-9]+).*$";

    /**
     * API URL ENDPOINT
     **/
    public static final String RENTAL_EDP_INSURANCE_CREATE = "/create";
    public static final String RENTAL_EDP_INSURANCE_PATCH_RN = "/patch-registration-number";
    public static final String RENTAL_EDP_INSURANCE_APPLY_COVERAGE = "/{insurancePolicy}/apply-coverage";
    public static final String RENTAL_EDP_INSURANCE_DOCUMENT_INFO = "/{rnVehicle}/document-info";

}
