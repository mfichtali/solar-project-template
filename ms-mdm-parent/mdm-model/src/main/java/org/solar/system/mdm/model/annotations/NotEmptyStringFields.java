package org.solar.system.mdm.model.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.solar.system.mdm.model.validators.NotEmptyStringFieldsValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyStringFieldsValidator.class)
public @interface NotEmptyStringFields {

	String message() default "List cannot contain null or empty fields";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
