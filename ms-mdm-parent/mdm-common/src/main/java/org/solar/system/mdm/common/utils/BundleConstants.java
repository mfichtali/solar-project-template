package org.solar.system.mdm.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BundleConstants {
	
	public static final String MDM_BAD_REQUEST_LOCKING					= "mdm.request.bad.lock_param";
	/** MDM CODE MESSAGE **/
	public static final String MDM_MODULE_CODE_NOT_EMPTY				= "mdm.module.register.code.notEmpty";
	public static final String MDM_MODULE_ID_NOT_FOUND 					= "mdm.module.id.notFound";
	public static final String MDM_MODULE_CODE_DUPLICATED				= "mdm.module.code.duplicated";
	public static final String MDM_MODULE_CODE_NOT_FOUND 				= "mdm.module.code.notFound";
	public static final String MDM_MODULE_ENDPOINT_MANDATORY			= "mdm.module.endpoint.mandatory";
	public static final String MDM_MODULE_VERSION_MANDATORY				= "mdm.module.version.mandatory";

	public static final String MDM_VERSION_NUMBER_DUPLICATED			= "mdm.module.version.number.duplicated";
    public static final String MDM_VERSION_MODULE_ID_NOT_FOUND 			= "mdm.version.module.id.notFound";
	public static final String MDM_VERSION_ID_NOT_FOUND 				= "mdm.version.id.notFound";
	public static final String MDM_VERSION_MODULE_CODE_NOT_FOUND 		= "mdm.version.module.code.notFound";
	public static final String MDM_VERSION_ALREADY_ACTIVE				= "mdm.module.version.already.active";
	public static final String MDM_VERSION_NOT_FOUND_IN_MODULE			= "mdm.version.module.code.notExist";
	public static final String MDM_ROUTE_NOT_FOUND_IN_MODULE			= "mdm.endpoint.module.code.notExist";

	public static final String MDM_PARAM_VERSION_ENDPOINT_MANDATORY		= "mdm.param-version.endpoint.mandatory";
	public static final String MDM_PARAM_VERSION_ID_NOT_FOUND 			= "mdm.param-version.id.notFound";
	public static final String MDM_PARAM_VERSION_ALREADY_EXIST 			= "mdm.param-version.already.exist";
	public static final String MDM_PARAM_VERSION_IS_LOCKED 				= "mdm.param-version.is.locked";
	public static final String MDM_PARAM_VERSION_NOT_ACTIVE 			= "mdm.param-version.not.active";
	public static final String MDM_PARAM_VERSION_ALREADY_LOCKED			= "mdm.param-version.already.locked";
	public static final String MDM_PARAM_VERSION_ALREADY_UNLOCKED		= "mdm.param-version.already.unlocked";
	
	public static final String MDM_ENDPOINT_URL_DUPLICATED				= "mdm.module.endpoint.url.duplicated";
	public static final String MDM_ENDPOINT_ID_NOT_FOUND				= "mdm.module.endpoint.id.notFound";
	public static final String MDM_ENDPOINT_ALREADY_ACTIVE				= "mdm.module.endpoint.already.active";

	public static final String MDM_VEHICLE_ENTITY_NOT_FOUND 			= "mdm.vehicle.entity.not.found";
	public static final String MDM_VEHICLE_ENTITY_IDENTIFIER_NOT_FOUND = "mdm.vehicle.entity.identifier.not.found";
	public static final String MDM_VEHICLE_ALREADY_RN_DEFINED 			= "mdm.vehicle.already.rn.defined";
	public static final String MDM_VEHICLE_ALREADY_RN_REGISTERED 		= "mdm.vehicle.already.rn.registered";
	public static final String MDM_GRAY_CARD_ALREADY_REGISTERED 		= "mdm.gray.card.already.registered";
	public static final String B_MDM_GRAY_CARD_ALREADY_REGISTERED 		= "{"+MDM_GRAY_CARD_ALREADY_REGISTERED+"}";
	public static final String B_MDM_VEHICLE_ALREADY_RN_REGISTERED 		= "{"+MDM_VEHICLE_ALREADY_RN_REGISTERED+"}";
	public static final String MDM_VEHICLE_RN_MANDATORY 				= "mdm.vehicle.rn.mandatory";

	public static final String MDM_CALENDAR_BILLING_CHECK_APPLY_YEAR 	= "mdm.calendar.check.applied.year";
	public static final String MDM_CALENDAR_BILLING_REQ_MANDATORY		= "mdm.calendar.billing.req.mandatory";
	public static final String MDM_CALENDAR_BILLING_REQ_INCOMPLETE 		= "mdm.calendar.billing.req.incomplete";
	public static final String MDM_CALENDAR_BILLING_ALREADY_EXISTS 		= "mdm.calendar.billing.already.exists";
	public static final String MDM_CALENDAR_BILLING_OF_YEAR_NOT_EXISTS	= "mdm.calendar.billing.of.year.not.exists";
	public static final String MDM_CALENDAR_BILLING_REQ_YEAR_MANDATORY 	= "mdm.calendar.billing.req.year.mandatory";
	public static final String MDM_CALENDAR_BILLING_NOT_FOUND 			= "mdm.calendar.billing.entity.not.found";
	public static final String MDM_CALENDAR_BILLING_SAME_YEAR 	 		= "mdm.calendar.billing.same.year";
	public static final String MDM_CALENDAR_BILLING_NOT_APPLIED	 		= "mdm.calendar.billing.not.applied";

	public static final String MDM_CAUTION_BILLING_NOT_APPLIED	 		= "mdm.caution.billing.not.applied";

	public static final String MDM_DOCUMENT_UPLOAD_SUCCESSFUL			= "mdm.document.upload.successful";
	public static final String MDM_DOCUMENT_ENTITY_NOT_FOUND			= "mdm.document.entity.not.found";
}
