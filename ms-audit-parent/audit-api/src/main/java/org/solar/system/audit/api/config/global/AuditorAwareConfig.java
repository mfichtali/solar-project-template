package org.solar.system.audit.api.config.global;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareConfig implements AuditorAware<String> {

	private static final String DEFAULT_USER = "SYSTEM";
	
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of(DEFAULT_USER);
	}

}
