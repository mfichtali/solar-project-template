package org.solar.system.central.common.vehicle.messaging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTSAVVehicleMessage implements Serializable {

    private String noTsav;
    private UUID idVehicle;
    private String rnVehicle;
    private UUID idBlobStorage;
    private LocalDate tsavDate;
    private LocalDate nextTsavDate;
    private String tsavComment;
    private BigDecimal tsavCost;

}
