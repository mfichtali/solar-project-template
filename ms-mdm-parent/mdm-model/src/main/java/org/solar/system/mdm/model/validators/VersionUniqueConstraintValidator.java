package org.solar.system.mdm.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.solar.system.central.common.mdm.request.VersionRefRequest;
import org.solar.system.mdm.model.annotations.VersionUniqueConstraint;

import java.util.ArrayList;
import java.util.List;

public class VersionUniqueConstraintValidator implements ConstraintValidator<VersionUniqueConstraint, List<VersionRefRequest>>{

	@Override
	public boolean isValid(List<VersionRefRequest> values, ConstraintValidatorContext context) {
		
		List<String> codes = new ArrayList<>();
		
		for (VersionRefRequest v : values) {
			String val = StringUtils.trimToEmpty(v.getVersionNumber());
			if(codes.contains(val)) {
				return false;
			}
			codes.add(val);
		}
		
		return true;
	}

}
