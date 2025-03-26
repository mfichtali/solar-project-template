package org.solar.system.central.common.all.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KafkaKeyLabelEnum {

    KEY_APP_TRACE_ACTION("k-trace-action"),

    KEY_HISTORIZE_DATA("k-historize-data-around-method"),
    KEY_AUDIT_ON_ERROR("k-audit-error"),
    KEY_RENTAL_HISTORICAL_VEHICLE("k-rental-historical-vehicle"),
    KEY_RENTAL_MANAGE_VEHICLE("k-rental-manage-vehicle"),
    
    KEY_QUERY_READ("k-read-query"),
    KEY_QUERY_CREATE("k-create-query"),
    KEY_QUERY_UPDATE("k-update-query"),
    KEY_QUERY_DELETE_LOGIC("k-delete-query")
    ;

    private final String keyLabel;

}
