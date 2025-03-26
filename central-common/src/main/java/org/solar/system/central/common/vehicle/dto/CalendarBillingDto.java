package org.solar.system.central.common.vehicle.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CalendarBillingDto implements Serializable {

    private String dueDate;
    private BigDecimal amount;
    private BigDecimal amountIncludingTax;
}
