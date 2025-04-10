package org.solar.system.hexa.infra.api.feign.mdm;

import org.solar.system.central.common.vehicle.containers.PmVehicleContainer;
import org.solar.system.hexa.infra.api.feign.common.AbstractClientFeign;
import org.solar.system.hexa.infra.api.feign.common.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.solar.system.hexa.infra.api.feign.common.AbstractClientFeign.MDM_SERVICE_NAME;
import static org.solar.system.hexa.infra.api.feign.common.AbstractClientFeign.MDM_SERVICE_URL;

@FeignClient(name = MDM_SERVICE_NAME, url = MDM_SERVICE_URL,
        configuration = FeignSupportConfig.class,
        fallbackFactory = MdmVehicleFallbackFactory.class)
public interface MdmVehicleClientFeign extends AbstractClientFeign {

    String GET_BASIC_INFO_VEHICLE = "/api/vehicle/basic-info";

    @GetMapping(GET_BASIC_INFO_VEHICLE)
    @ResponseStatus(HttpStatus.OK)
    PmVehicleContainer.VehicleBasicInfoRecord loadVehicleBasicInfoByRegistrationNumber(final @RequestParam(value = "rn-vehicle") String registrationNumber);

    @GetMapping(GET_BASIC_INFO_VEHICLE)
    @ResponseStatus(HttpStatus.OK)
    PmVehicleContainer.VehicleBasicInfoRecord loadVehicleBasicInfoByIdentifier(final @RequestParam(value = "vehicle-id") String vehicleId);

}
