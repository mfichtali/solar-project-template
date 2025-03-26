package org.solar.system.mdm.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.solar.system.central.common.mdm.request.EndpointRefRequest;
import org.solar.system.mdm.model.annotations.EndpointUniqueConstraint;

import java.util.ArrayList;
import java.util.List;

public class EndpointUniqueConstraintValidator implements ConstraintValidator<EndpointUniqueConstraint, List<EndpointRefRequest>> {

	@Override
	public boolean isValid(List<EndpointRefRequest> values, ConstraintValidatorContext context) {
		
		if(CollectionUtils.isEmpty(values)) {
			return true;
		}
		
		List<String> codes = new ArrayList<>();
		
		for (EndpointRefRequest v : values) {
			String url = StringUtils.trimToEmpty(v.getUrl());
			if(codes.contains(url)) {
				return false;
			}
			codes.add(url);
		}
		
		return true;
	}

}
