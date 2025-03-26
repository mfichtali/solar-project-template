package org.solar.system.central.common.all.exceptions;

import lombok.Getter;
import org.solar.system.central.common.audit.enums.AuditErrorTypeEnum;

@Getter
public class InvalidKafkaKeyException extends RuntimeException {

	private final AuditErrorTypeEnum errorType;

	public InvalidKafkaKeyException(String msg) {
		super(msg);
		this.errorType = AuditErrorTypeEnum.TECHNICAL;
	}
	
	public InvalidKafkaKeyException(String msg, Throwable cause) {
		super(msg, cause);
		this.errorType = AuditErrorTypeEnum.TECHNICAL;
	}
    
}
