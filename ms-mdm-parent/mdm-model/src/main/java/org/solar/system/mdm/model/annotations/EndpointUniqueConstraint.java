package org.solar.system.mdm.model.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.solar.system.mdm.model.validators.EndpointUniqueConstraintValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Constraint(validatedBy = { EndpointUniqueConstraintValidator.class })
@Retention(RetentionPolicy.RUNTIME)
public @interface EndpointUniqueConstraint {

	String message() default "The list must not contain duplicate endpoint";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
