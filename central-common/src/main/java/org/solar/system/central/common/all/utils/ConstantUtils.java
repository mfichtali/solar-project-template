package org.solar.system.central.common.all.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantUtils {

	/** Common Constant */

	public static final String PATTERN_API_VERSION_URL_REGEX		= "^(/api/v[0-9]+\\\\.[0-9]+/[A-Za-z0-9]+).*$";
	public static final String PATTERN_MDM_PROPERTY_VALIDATOR 		= "(.*?)(\\[\\d\\]).(.*)";
	public static final String PATTERN_INDEX_PROPERTY_VALIDATOR 	= "(.*?)(\\[[0-9]+\\])";
	public static final String PATTERN_EXPRESSION_BETWEEN_BRACKET 	= "(\\{.*?\\})";
	public static final String EMAIL_PATTERN 						= "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
	public static final String RQ_USE_CACHE 						= "use-cache";
	public static final String CACHE_PREFIX 						= "cache";
	public static final String CIRCUIT_BREAKER_NAME 				= "gwt-circuit-breaker";
	public static final String CIRCUIT_BREAKER_FALLBACK_URL			= "forward:/%s/fallback";
	public static final String GATEWAY_X_HEADER_USERNAME			= "x-username";
	public static final String GATEWAY_X_HEADER_AUTHORITIES			= "x-authorities";

	/** Kafka Constant Start */
	
	public static final String MAIN_TOPIC 						= "main.topic";
	public static final String RETRY_TOPIC 						= "retry.topic";
	public static final String DLT_TOPIC 						= "dlt.topic";
	public static final String X_GROUPS_HEADER 					= "X-Groups-Header";
	public static final String AUDIT_GROUP_ID 					= "auditGroup";
	public static final String MDM_GROUP_ID 					= "mdmGroup";
	public static final String RENTAL_GROUP_ID = "rentalGroup";

	public static final String KEY_HISTORIZE 					= "k-historize-param-input";
	public static final String KEY_AUDIT_ERROR 					= "k-audit-error";
	public static final String KEY_RENTAL_HISTORICAL_VEHICLE 	= "k-rental-historical-vehicle";

	public static final String PARTITION_QUERY_READ 			= "3";
	public static final String PARTITION_QUERY_CREATE 			= "5";
	public static final String PARTITION_HISTORIZE 				= "6";
	public static final String PARTITION_QUERY_UPDATE 			= "7";
	public static final String PARTITION_QUERY_DELETE 			= "11";
	public static final String PARTITION_AUDIT_ERROR 			= "2";


	/** Kafka Constant End */
	
	/** Start Custom Annotation business */

	public static final String ANNOTATION_LOG_EXECUTION_TIME = "@annotation(org.solar.system.central.common.all.annotations.LogExecutionTime)";
	public static final String ANNOTATION_HISTORIZE_DATA = "@annotation(org.solar.system.central.common.all.annotations.HistorizeDataPerform)";
	
	/** End Custom Annotation business */

	public static final String RESOURCE_NOT_YET_IMPLEMENTED 	= "Resource not yet implemented!";
	public static final String MS_CHECKER_MESSAGE 				= "IT'S WORK";
	public static final String MS_CHECKER_UP 					= "MS-UP";
	public static final String MS_KAFKA_UP 						= "Kafka is running.";
	public static final String MS_KAFKA_DOWN 					= "Kafka is down.";
	public static final String SERVICE_UNAVAILABLE_MSG 			= "SERVICE %s MOMENTANÉMENT INDISPONIBLE. VEUILLEZ RÉESSAYER PLUS TARD";

}
