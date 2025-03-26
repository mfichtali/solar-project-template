package org.solar.system.central.common.all.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TableNamesUtils {

    /**
     * MDM - Start
     **/

    public static final String MDM_DEFAULT_SCHEMA = "public";
    public static final String AUTH_DEFAULT_SCHEMA = "public";

    /**
     * REFERENTIAL TABLES
     **/
    public static final String MODULE_REF = "ref_module";
    public static final String VERSION_REF = "ref_version";
    public static final String ENDPOINT_REF = "ref_endpoint";

    /**
     * PARAM TABLES
     **/
    public static final String T_MONIT_PARAM_VERSION = "t_monitoring_param_version";

    /**
     * VEHICLE TABLES
     **/
    public static final String VEHICLE_REF = "ref_vehicle";
    public static final String GRAY_CARD_REF = "ref_gray_card";
    public static final String CALENDAR_BILLING_REF = "ref_calendar_billing";
    public static final String CAUTION_BILLING_REF = "ref_caution_billing";
    public static final String T_HIST_EVENT_VEHICLE = "t_vehicle_event_hist";
    public static final String T_BLOB_STORAGE = "t_blob_storage";

    /**
     * SCHEDULE TASK TABLES
     **/
    public static final String T_SCHEDULE_TASK_TX_OUTBOX = "t_schedule_task_tx_outbox";


    /** MDM - End **/

    /**
     * AUDIT - Start
     **/

    public static final String AUDIT_DEFAULT_SCHEMA = "public";

    /**
     * AUDIT TABLES
     **/
    public static final String T_AUDIT_EVENT = "t_audit_event";
    public static final String T_AUDIT_ERROR_EVENT = "t_audit_error_event";

    /**
     * RENTAL TABLES
     **/
    public static final String RENTAL_DEFAULT_SCHEMA = "public";

    public static final String T_INSPECTION_VEHICLE = "t_inspection_vehicle";
    public static final String T_TSAV_STICKER_VEHICLE = "t_tsav_vehicle";
    public static final String T_INSURANCE_VEHICLE = "t_insurance_vehicle";
    public static final String T_INSURANCE_COMPANY = "t_insurance_company";
    public static final String T_INSURANCE_COVERAGE = "t_insurance_coverage";
    public static final String T_INSURANCE_OWNER = "t_insurance_owner";
    public static final String T_INSURANCE_SINISTER = "t_insurance_sinister";
    public static final String T_INSURANCE_SINISTER_EVENT = "t_insurance_sinister_event";


}
