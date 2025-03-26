package org.solar.system.central.common.all.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GatewayServiceEnum {

	REGISTRY("eureka-service", "EURK", null, "EURK-SERVICE", "Eureka-Registry-Service"),
	CONF("config-service", "CONFIG", null, "CONFIG-SERVER", "Config-Server"),
	GTW("gwt-service", "GTW", null, "GTW-SERVICE", "API Gateway"),


	MDM("mdm-service","MDM","/mdm/**", "MDM-SERVICE", "Master Data Management Service"),
	RENTAL("rental-service","RENTAL","/rental/**", "RENTAL-SERVICE", "Rental Service Provider"),
	AUTH("auth-service","AUTH","/auth/**", "AUTH-SERVICE", "Authentication Service Provider"),
	AUDIT("audit-service", "AUDIT", "/audit/**", "AUDIT-SERVICE", "Audit Service Provider"),


	;
	
	public final String serviceId;
	public final String serviceCode;
	public final String servicePath;
	public final String serviceName;
	public final String serviceSummary;

}
