package org.solar.system.central.common.all.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

import static org.solar.system.central.common.all.enums.KafkaKeyLabelEnum.KEY_APP_TRACE_ACTION;
import static org.solar.system.central.common.all.enums.KafkaKeyLabelEnum.KEY_AUDIT_ON_ERROR;
import static org.solar.system.central.common.all.enums.KafkaKeyLabelEnum.KEY_HISTORIZE_DATA;
import static org.solar.system.central.common.all.enums.KafkaKeyLabelEnum.KEY_QUERY_CREATE;
import static org.solar.system.central.common.all.enums.KafkaKeyLabelEnum.KEY_QUERY_DELETE_LOGIC;
import static org.solar.system.central.common.all.enums.KafkaKeyLabelEnum.KEY_QUERY_READ;
import static org.solar.system.central.common.all.enums.KafkaKeyLabelEnum.KEY_QUERY_UPDATE;
import static org.solar.system.central.common.all.enums.KafkaKeyLabelEnum.KEY_RENTAL_HISTORICAL_VEHICLE;
import static org.solar.system.central.common.all.enums.KafkaKeyLabelEnum.KEY_RENTAL_MANAGE_VEHICLE;

@AllArgsConstructor
@Getter
public enum KafkaPartitionEnum {

    ZERO("0", Set.of(KEY_HISTORIZE_DATA, KEY_APP_TRACE_ACTION)),
    ONE("1", Set.of(KEY_RENTAL_HISTORICAL_VEHICLE, KEY_RENTAL_MANAGE_VEHICLE)),
    TWO("2", Set.of()),
    THREE("3", Set.of(KEY_QUERY_READ,
            KEY_QUERY_CREATE, KEY_QUERY_UPDATE,
            KEY_QUERY_DELETE_LOGIC)),

    FOUR("4", Set.of()),
    FIVE("5", Set.of()),
    SIX("6", Set.of()),
    SEVEN("7", Set.of()),
    EIGHT("8", Set.of()),
    NINE("9", Set.of()),
    TEN("10", Set.of()),

    ELEVEN("11", Set.of(KEY_AUDIT_ON_ERROR))

    ;

    public static final String PARTITION_ZERO          = "0";
    public static final String PARTITION_ONE         = "1";
    public static final String PARTITION_THREE         = "3";
    public static final String PARTITION_ELEVEN        = "11";

    private final String partition;
    private final Set<KafkaKeyLabelEnum> kafkaKeys;

    public static KafkaPartitionEnum enumByKafkaKey(KafkaKeyLabelEnum kafkaKey) {
        return Arrays.stream(values())
                .filter(value -> value.kafkaKeys.contains(kafkaKey))
                .findFirst()
                .orElse(null);
	}

}
