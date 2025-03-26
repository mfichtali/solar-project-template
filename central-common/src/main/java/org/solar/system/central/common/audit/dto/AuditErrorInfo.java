package org.solar.system.central.common.audit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.solar.system.central.common.audit.enums.AuditErrorTypeEnum;


@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditErrorInfo {
    private AuditErrorTypeEnum errorType;
    private String errorMessage;
    private String classExceptionName;
}
