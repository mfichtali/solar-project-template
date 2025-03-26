package org.solar.system.mdm.common.enums;

import lombok.Getter;

@Getter
public enum EndpointLocking {

	/**
	 * Lock = True flag
	 */
	LOCK(Boolean.TRUE),

	/**
	 * unlock = False flag
	 */
	UNLOCK(Boolean.FALSE),

	/**
	 * NONE = Undefined NULL
	 */
	NONE(null)

	;

	private final Boolean value;

	EndpointLocking(Boolean value) {
		this.value = value;
	}

}
