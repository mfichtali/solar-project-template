package org.solar.system.mdm.api.rest;

import org.solar.system.mdm.service.base.AbstractCommon;

public abstract class AbstractResource extends AbstractCommon {

    public static final String PARAM_VERSION_RESOURCE = "apiVersionResource";
    public static final String VEHICLE_RESOURCE = "apiVehicleResource";
    public static final String MDM_API_KEY = "${application.endpoint-api-key}";

    public static final String MDM_PARAM_VERSION_REQUEST_MAPPING = MDM_API_KEY + "/param-version";
    public static final String MDM_VEHICLE_REQUEST_MAPPING = MDM_API_KEY + "/vehicle";

}
