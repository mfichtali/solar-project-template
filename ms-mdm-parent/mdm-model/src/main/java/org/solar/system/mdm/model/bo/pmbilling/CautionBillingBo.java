package org.solar.system.mdm.model.bo.pmbilling;

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
public class CautionBillingBo implements Serializable {

    private LocalDate startCautionDate;
    private LocalDate endCautionDate;
    private BigDecimal amount;
    private BigDecimal amountIncludingTax;

}
