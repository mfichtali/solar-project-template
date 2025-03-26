package org.solar.system.central.common.all.messaging;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum KafkaGroupHeaderEnum {
    
    MDM_2_ALL,
    MDM_2_AUDIT,
    MDM_2_AUTH,
    AUTH_2_AUDIT,
    RENTAL_2_MDM,
    RENTAL_2_AUDIT,
    MDM_2_RENTAL,

    ;

    public static List<String> groupAudit() {
        return Arrays.stream(KafkaGroupHeaderEnum.values())
            .map(KafkaGroupHeaderEnum::name)
            .filter(name -> name.endsWith("AUDIT"))
            .collect(Collectors.toList());
    }

    public static List<String> groupMdm() {
        return Arrays.stream(KafkaGroupHeaderEnum.values())
            .map(KafkaGroupHeaderEnum::name)
            .filter(name -> name.endsWith("MDM"))
            .collect(Collectors.toList());
    }

    public static List<String> groupRental() {
        return Arrays.stream(KafkaGroupHeaderEnum.values())
                .map(KafkaGroupHeaderEnum::name)
                .filter(name -> name.endsWith("RENTAL"))
                .collect(Collectors.toList());
    }

    public static List<String> groupAuth() {
        return Arrays.stream(KafkaGroupHeaderEnum.values())
                .map(KafkaGroupHeaderEnum::name)
                .filter(name -> name.endsWith("AUTH"))
                .collect(Collectors.toList());
    }

    public static String filterOfAudit(String... groups) {
        return Arrays.stream(groups).filter(name -> name.endsWith("AUDIT")).findFirst().orElse(null);
    }

    public static String filterOfMdm(String... groups) {
        return Arrays.stream(groups).filter(name -> name.endsWith("MDM")).findFirst().orElse(null);
    }

    public static String filterOfRental(String... groups) {
        return Arrays.stream(groups).filter(name -> name.endsWith("RENTAL")).findFirst().orElse(null);
    }

    public static KafkaGroupHeaderEnum fromString(String value) {
        return Arrays.stream(values())
				.filter(i -> i.name().equals(value))
				.findFirst()
				.orElse(null);
    }

    public static String keysAsString(KafkaGroupHeaderEnum... group) {
        return Arrays.stream(group).map(KafkaGroupHeaderEnum::name).collect(Collectors.joining(","));
    }
}
