package org.solar.system.central.common.all.enums;

public enum KafkaPartitionLabelEnum {

    PARTITION_QUERY_READ,
    PARTITION_QUERY_CREATE,
    PARTITION_QUERY_UPDATE,
    PARTITION_QUERY_DELETE,

    PARTITION_HISTORIZE,

    PARTITION_AUDIT_ON_ERROR,

    PARTITION_MS_ACTION,

    ;


    public static KafkaPartitionLabelEnum[] queryPartitions() {
        return new KafkaPartitionLabelEnum[]{
                PARTITION_QUERY_READ,
                PARTITION_QUERY_CREATE,
                PARTITION_QUERY_UPDATE,
                PARTITION_QUERY_DELETE
        };
    }

    public static KafkaPartitionLabelEnum[] historizePartitions() {
        return new KafkaPartitionLabelEnum[]{
                PARTITION_HISTORIZE
        };
    }

}
