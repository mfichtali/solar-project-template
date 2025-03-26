package org.solar.system.mdm.model.bo.pmbilling;

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
public class CalendarBillingBo implements Serializable {

    @Setter(AccessLevel.NONE)
    private String month;

    @Setter(AccessLevel.NONE)
    private String year;
    private String dueDate;
    private BigDecimal amount;
    private BigDecimal amountIncludingTax;

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
        this.year = StringUtils.substring(this.dueDate,0, 4);
        this.month = StringUtils.substring(this.dueDate,4);

    }
}
