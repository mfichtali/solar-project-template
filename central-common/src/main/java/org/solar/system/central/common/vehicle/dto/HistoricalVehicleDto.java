package org.solar.system.central.common.vehicle.dto;

import lombok.*;
import lombok.experimental.Accessors;
import org.solar.system.central.common.vehicle.enums.EvenementVehicleTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class HistoricalVehicleDto implements Serializable {

    private EvenementVehicleTypeEnum eventType;
//.date(DateUtils.toStringFormat(LocalDateTime.now()))//
    private String startEventDate;

    private String endEventDate;

    private String summaryEvent;

}
