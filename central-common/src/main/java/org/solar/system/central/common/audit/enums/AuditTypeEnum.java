package org.solar.system.central.common.audit.enums;

public enum AuditTypeEnum {

	QUERY,
	TRACE,
	ERROR
	;



	public static class Values {
		public static final String ERROR = "ERROR";
		public static final String TRACE = "TRACE";
		public static final String QUERY = "QUERY";
	}

}
