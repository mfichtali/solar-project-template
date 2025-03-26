package org.solar.system.central.common.all.exceptions;

public class ServiceUnavailableException extends TechnicalException {

	/**
	 * Constructs a <code>ServiceUnavailableException</code> with the specified message.
	 * @param msg the detail message.
	 */
	public ServiceUnavailableException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code ServiceUnavailableException} with the specified message and root
	 * cause.
	 * @param msg the detail message.
	 * @param cause root cause
	 */
	public ServiceUnavailableException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
