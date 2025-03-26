package org.solar.system.mdm.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class VehicleTSAVDocumentRequest extends VehicleDocumentRequest implements Serializable {

    private String noTsav;
    private LocalDate tsavDate;
    private LocalDate nextTsavDate;
    private String tsavComment;
    private BigDecimal tsavCost;
    
}
