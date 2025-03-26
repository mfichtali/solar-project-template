package org.solar.system.central.common.auth.enums;

public enum AuthNamesEnum {

	ALGORITHM("algorithm"), 
	PUBLIC_KEY("publicKey"),

	;

	public final String label;

	private AuthNamesEnum(String label) {
		this.label = label;
	}

}
