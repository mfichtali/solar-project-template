package org.solar.system.mdm.model.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.solar.system.mdm.model.validators.ModuleUniqueConstraintValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, PARAMETER})
@Constraint(validatedBy = { ModuleUniqueConstraintValidator.class })
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleUniqueConstraint {

	String message() default "The list must not contain duplicate code modules";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
