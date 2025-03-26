package org.solar.system.audit.api.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${application.endpointApikey}")
public class AuditResource extends AbstractResource {
	
	
}
