package org.solar.system.central.common.all.exceptions;

import lombok.Getter;
import org.solar.system.central.common.audit.enums.AuditErrorTypeEnum;

@Getter
public class BusinessException extends RuntimeException {

	private final AuditErrorTypeEnum errorType;

	public BusinessException(String msg) {
		super(msg);
		this.errorType = AuditErrorTypeEnum.FUNCTIONAL;
	}
	
	public BusinessException(String msg, Throwable cause) {
		super(msg, cause);
		this.errorType = AuditErrorTypeEnum.FUNCTIONAL;
	}
}
