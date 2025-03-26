package org.solar.system.central.common.all.exceptions;

import lombok.Getter;
import org.solar.system.central.common.audit.enums.AuditErrorTypeEnum;

@Getter
public class TechnicalException extends RuntimeException{

	private final AuditErrorTypeEnum errorType;

	public TechnicalException(String msg) {
		super(msg);
		this.errorType = AuditErrorTypeEnum.TECHNICAL;
	}
	
	public TechnicalException(String msg, Throwable cause) {
		super(msg, cause);
		this.errorType = AuditErrorTypeEnum.TECHNICAL;
	}
    
}
