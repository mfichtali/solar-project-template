package org.solar.system.hexa.domain.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BundleConstants {

    public static final String RENTAL_NOT_YET_IMPLEMENTED = "rental.common.resource.notImplemented";
    public static final String RENTAL_VERSION_NOT_PRESENT = "rental.retrieve.version.notPresent";
    public static final String RENTAL_ENDPOINT_NOT_HANDLED = "rental.retrieve.endpoint.notHandled";
    public static final String RENTAL_VERSION_NOT_SUPPORTED = "rental.retrieve.version.notSupported";

    public static final String RENTAL_IV_DOCUMENT_TYPE_NOT_SUPPORTED = "rental.iv.document.type.not.supported";
    public static final String RENTAL_COMPANY_ENTITY_NOT_FOUND = "rental.company.entity.not.found";
    public static final String RENTAL_IV_ENTITY_NOT_FOUND = "rental.iv.policy.entity.not.found";
    public static final String RENTAL_IV_DATE_ALREADY_COVERED = "rental.iv.currently.available";
    public static final String RENTAL_IV_POLICY_MANDATORY = "rental.iv.policy.mandatory";
    public static final String RENTAL_CREATE_INSURANCE_DATE_VALIDATION = "rental.validation.create.insurance.date";
    public static final String RENTAL_REF_VEHICLE_NOT_FOUND = "rental.ref.vehicle.not.found";

}
