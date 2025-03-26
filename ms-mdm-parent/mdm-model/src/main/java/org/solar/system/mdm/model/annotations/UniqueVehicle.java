package org.solar.system.mdm.model.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.solar.system.mdm.common.utils.BundleConstants;
import org.solar.system.mdm.model.validators.UniqueVehicleValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueVehicleValidator.class })
public @interface UniqueVehicle {

    String message() default BundleConstants.B_MDM_VEHICLE_ALREADY_RN_REGISTERED;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
