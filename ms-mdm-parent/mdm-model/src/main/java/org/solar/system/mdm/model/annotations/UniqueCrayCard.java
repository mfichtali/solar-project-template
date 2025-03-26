package org.solar.system.mdm.model.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.solar.system.mdm.common.utils.BundleConstants;
import org.solar.system.mdm.model.validators.UniqueGrayCardValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueGrayCardValidator.class })
public @interface UniqueCrayCard {

    String message() default BundleConstants.B_MDM_GRAY_CARD_ALREADY_REGISTERED;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
