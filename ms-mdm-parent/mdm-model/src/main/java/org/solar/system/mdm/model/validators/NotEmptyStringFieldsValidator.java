package org.solar.system.mdm.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.solar.system.mdm.model.annotations.NotEmptyStringFields;

import java.util.List;

public class NotEmptyStringFieldsValidator implements ConstraintValidator<NotEmptyStringFields, List<String>> {

    @Override
    public boolean isValid(List<String> objects, ConstraintValidatorContext context) {
        return objects.stream().allMatch(StringUtils::isNotBlank);
    }

}
