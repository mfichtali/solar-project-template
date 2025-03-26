package org.solar.system.mdm.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.solar.system.mdm.model.annotations.UniqueCrayCard;
import org.solar.system.mdm.model.bo.vehicle.GrayCardBo;
import org.solar.system.mdm.model.repository.vehicle.GrayCardRefRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueGrayCardValidator implements ConstraintValidator<UniqueCrayCard, GrayCardBo> {

    @Autowired
    private GrayCardRefRepository repository;

    @Override
    public boolean isValid(GrayCardBo grayCard, ConstraintValidatorContext context) {

        String serialOperationNumber = grayCard.getSerialOperationNumber();
        return !repository.existsBySerialOperationNumber(serialOperationNumber);
    }
}
