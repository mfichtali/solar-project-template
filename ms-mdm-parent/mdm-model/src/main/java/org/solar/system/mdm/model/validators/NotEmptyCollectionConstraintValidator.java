package org.solar.system.mdm.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.solar.system.mdm.model.annotations.NotEmptyCollectionConstraint;

import java.util.List;

public class NotEmptyCollectionConstraintValidator implements ConstraintValidator<NotEmptyCollectionConstraint, List<?>> {

	private int min;
	
	@Override
	public void initialize(NotEmptyCollectionConstraint constraintAnnotation) {
		this.min = constraintAnnotation.min();
	}
	
	@Override
	public boolean isValid(List<?> list, ConstraintValidatorContext context) {
		return list.size() >= this.min;
	}
	
}
