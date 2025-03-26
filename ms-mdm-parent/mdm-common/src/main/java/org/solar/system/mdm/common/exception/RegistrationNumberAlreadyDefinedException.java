package org.solar.system.mdm.common.exception;

import org.solar.system.central.common.all.exceptions.BusinessException;

public class RegistrationNumberAlreadyDefinedException extends BusinessException {

    public RegistrationNumberAlreadyDefinedException(String msg) {
        super(msg);
    }
}
