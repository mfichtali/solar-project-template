package org.solar.system.mdm.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.solar.system.mdm.model.annotations.UniqueVehicle;
import org.solar.system.mdm.model.bo.vehicle.VehicleBo;
import org.solar.system.mdm.model.repository.vehicle.VehicleRefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueVehicleValidator implements ConstraintValidator<UniqueVehicle, VehicleBo> {

    @Autowired
    private VehicleRefRepository repository;

    @Override
    public boolean isValid(VehicleBo vehicleBo, ConstraintValidatorContext context) {

        boolean valid;
        String wwRegistrationNumber = vehicleBo.getWwRegistrationNumber();
        String registrationNumber = vehicleBo.getRegistrationNumber();
        String serialChassisNumber = vehicleBo.getSerialChassisNumber();

        valid = !(repository.existsByWwRegistrationNumber(wwRegistrationNumber) ||
                (StringUtils.isNotBlank(registrationNumber) &&
                        repository.existsByRegistrationNumber(registrationNumber)) ||
                repository.existsBySerialChassisNumber(serialChassisNumber));

        return valid;
    }
}
