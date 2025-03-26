package org.solar.system.mdm.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.solar.system.mdm.model.annotations.UniqueCrayCard;
import org.solar.system.mdm.model.annotations.UniqueVehicle;
import org.solar.system.mdm.model.bo.vehicle.GrayCardBo;
import org.solar.system.mdm.model.bo.vehicle.VehicleBo;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class VehicleInfoRequest implements Serializable {

    @UniqueVehicle
    private VehicleBo vehicle;

    @UniqueCrayCard
    private GrayCardBo grayCard;

}


