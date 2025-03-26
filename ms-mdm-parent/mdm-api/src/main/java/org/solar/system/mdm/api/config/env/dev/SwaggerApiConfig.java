package org.solar.system.mdm.api.config.env.dev;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("dev")
public class SwaggerApiConfig {

	@Value("${application.swagger.title}") private String applicationTitle;
	@Value("${application.swagger.description}") private String applicationDescription;
	@Value("${application.swagger.termsOfService}") private String termsOfService;
	@Value("${application.swagger.licenseName}") private String licenseName;
	@Value("${application.swagger.licenseUrl}") private String licenseUrl;
	@Value("${application.swagger.activeVersion}") private String applicationVersion;

	@Value("${application.swagger.actuator.group-label}") private String actuatorGroup;
	@Value("${application.swagger.actuator.paths-to-match}") private String actuatorPathToMatch;
	@Value("${application.swagger.actuator.paths-to-exclude:}") private String actuatorPathToExclude;

	@Value("${application.swagger.admin.group-label}") private String adminGroup;
	@Value("${application.swagger.admin.paths-to-match:/api/admin/**}") private List<String> adminPathsToMatch;

	@Value("${application.swagger.mdm-param-version.group-label}") private String mdmParamVersionGroup;
	@Value("${application.swagger.mdm-param-version.paths-to-match}") private List<String> mdmParamVersionPathsToMatch;
	@Value("${application.swagger.mdm-param-version.paths-to-exclude:}") private List<String> mdmParamVersionPathsToExclude;

	@Value("${application.swagger.mdm-vehicle.group-label}") private String mdmVehicleGroup;
	@Value("${application.swagger.mdm-vehicle.paths-to-match}") private List<String> mdmVehiclePathsToMatch;
	@Value("${application.swagger.mdm-vehicle.paths-to-exclude:}") private List<String> mdmVehiclePathsToExclude;

	@Bean
	OpenAPI beanOpenApi() {
		return new OpenAPI()
				.info(apiInfo());
	}

	@Bean
	GroupedOpenApi actuatorAreaGroup() {
		return GroupedOpenApi.builder()
				.group(actuatorGroup)
				.pathsToMatch(actuatorPathToMatch)
				.build();
	}

	@Bean
	GroupedOpenApi adminMdmAreaGroup() {
		return GroupedOpenApi.builder()
				.group(adminGroup)
				.pathsToMatch(adminPathsToMatch.toArray(String[]::new))
				.build();
	}

	@Bean
	GroupedOpenApi monitParamVersionMdmAreaGroup() {
		return GroupedOpenApi.builder()
				.group(mdmParamVersionGroup)
				.pathsToMatch(mdmParamVersionPathsToMatch.toArray(String[]::new))
				.build();
	}
	@Bean
	GroupedOpenApi vehicleMdmAreaGroup() {
		return GroupedOpenApi.builder()
				.group(mdmVehicleGroup)
				.pathsToMatch(mdmVehiclePathsToMatch.toArray(String[]::new))
				.build();
	}

	private Info apiInfo() {
		return new Info()
				.title(applicationTitle)
				.version(applicationVersion)
				.description(applicationDescription)
				.termsOfService(termsOfService)
				.license(apiLicense());
	}

	private License apiLicense() {
		return new License().name(licenseName).url(licenseUrl);
	}

}
