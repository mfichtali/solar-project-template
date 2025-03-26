package org.solar.system.central.common.vehicle.request;


import jakarta.validation.constraints.NotNull;
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
public class CautionBillingRequest implements Serializable {

    @NotNull
    private LocalDate startCautionDate;

    @NotNull
    private BigDecimal amountCautionNoTax;

}
