package org.solar.system.hexa.infra.api.feign.common;

public interface AbstractClientFeign {

    String MDM_SERVICE_NAME = "${application.b2b.feign.mdm.service-name:}";
    String MDM_SERVICE_URL = "${application.b2b.feign.mdm.service-url:}";
}
