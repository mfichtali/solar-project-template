package org.solar.system.mdm.common.exception;

import org.solar.system.central.common.all.exceptions.BusinessException;

public class VersionNumberDuplicateException extends BusinessException {

	public VersionNumberDuplicateException(String msg) {
		super(msg);
	}

}