package org.solar.system.audit.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractResource {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Value("${spring.profiles.active}")
	protected String activeProfile;
	
}
