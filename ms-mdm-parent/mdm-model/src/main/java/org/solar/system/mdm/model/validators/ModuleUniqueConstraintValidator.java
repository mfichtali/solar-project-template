package org.solar.system.mdm.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.solar.system.central.common.mdm.request.ModuleInfoRequest;
import org.solar.system.mdm.model.annotations.ModuleUniqueConstraint;

import java.util.ArrayList;
import java.util.List;

public class ModuleUniqueConstraintValidator implements ConstraintValidator<ModuleUniqueConstraint, List<ModuleInfoRequest>> {

	@Override
	public boolean isValid(List<ModuleInfoRequest> moduleList, ConstraintValidatorContext context) {

		if(CollectionUtils.isEmpty(moduleList)) {
			return true;
		}

		List<String> codes = new ArrayList<>();
		
		for (ModuleInfoRequest module : moduleList) {
			String m = StringUtils.trimToEmpty(module.getCode());
			if(codes.contains(m)) {
				return false;
			}
			codes.add(m);
		}
		
		return true;
	}

}
