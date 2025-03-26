package org.solar.system.central.common.audit.enums;

public enum AuditActionEnum {

	C, // create
	U, // Update
	D, // Delete
	X, // Execute
	R, // Read
	;

	public static class Values {
		public static final String CREATE 	= "C";
		public static final String UPDATE 	= "U";
		public static final String DELETE 	= "D";
		public static final String EXECUTE 	= "X";
		public static final String READ 	= "R";
	}
}
