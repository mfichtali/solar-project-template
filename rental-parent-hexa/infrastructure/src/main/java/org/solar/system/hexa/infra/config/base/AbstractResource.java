package org.solar.system.hexa.infra.config.base;


public abstract class AbstractResource extends AbstractCommon {


    public static final String REST_RENTAL_INSURANCE_RESOURCE = "rentalInsuranceResource";
    public static final String RENTAL_API_KEY = "${application.endpoint-api-key}";

    public static final String RENTAL_INSURANCE_KEY_MAPPING = "/insurance-vehicle";
    public static final String RENTAL_INSURANCE_REQUEST_MAPPING = RENTAL_API_KEY + RENTAL_INSURANCE_KEY_MAPPING;

    public static final String REST_RENTAL_INSURANCE_RESOURCE_V1_0 = "rentalInsuranceResourceV1_0";
    public static final String RENTAL_INSURANCE_REQUEST_MAPPING_V1_0 = RENTAL_API_KEY + "/v1.0" + RENTAL_INSURANCE_KEY_MAPPING;

}
