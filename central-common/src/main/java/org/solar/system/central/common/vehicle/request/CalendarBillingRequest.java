package org.solar.system.central.common.vehicle.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CalendarBillingRequest implements Serializable {

    @Setter(AccessLevel.NONE)
    private String year;
    private String dueDate;
    private BigDecimal amountRentalNoTax;

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
        this.year = StringUtils.substring(this.dueDate,0, 4);
    }
}
