package org.solar.system.central.common.vehicle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class VehicleBasicInfoDto implements Serializable {

    private UUID vehicleId;

    private String registrationNumber;
}
