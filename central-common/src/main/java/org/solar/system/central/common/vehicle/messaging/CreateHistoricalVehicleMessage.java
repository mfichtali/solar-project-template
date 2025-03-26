package org.solar.system.central.common.vehicle.messaging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.solar.system.central.common.vehicle.enums.EvenementVehicleTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateHistoricalVehicleMessage implements Serializable {

    private String registrationNumber;
    private EvenementVehicleTypeEnum eventType;
    private LocalDateTime startEvent = LocalDateTime.now();
    private LocalDateTime endEvent = LocalDateTime.now();

}
