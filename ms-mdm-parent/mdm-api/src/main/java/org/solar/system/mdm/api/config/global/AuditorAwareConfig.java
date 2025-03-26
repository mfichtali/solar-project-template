package org.solar.system.mdm.api.config.global;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareConfig implements AuditorAware<String> {
	
	private static final String DEFAULT_USER = "SYSTEM";

	@Override
	public Optional<String> getCurrentAuditor() {
		
		/**
		 * Write logic to retreive user
		 */
		
		return Optional.of(DEFAULT_USER);
	}

}
