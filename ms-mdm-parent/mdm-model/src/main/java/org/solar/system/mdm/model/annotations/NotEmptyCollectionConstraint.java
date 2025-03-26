package org.solar.system.mdm.model.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.solar.system.mdm.model.validators.NotEmptyCollectionConstraintValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { NotEmptyCollectionConstraintValidator.class })
public @interface NotEmptyCollectionConstraint {

	String message() default "The input list must contain at least {min} elements";
	
	int min() default 1;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
}
