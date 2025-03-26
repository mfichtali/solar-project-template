package org.solar.system.mdm.model.bo.vehicle;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class VehicleBasicInfoBo implements Serializable {

    private UUID vehicleId;
    private String registrationNumber;

}
