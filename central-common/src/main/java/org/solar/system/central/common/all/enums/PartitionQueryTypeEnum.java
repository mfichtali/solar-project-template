package org.solar.system.central.common.all.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum PartitionQueryTypeEnum {
    
    READ ("k-read-query", 3),
    CREATE ("k-create-query", 5),
    UPDATE ("k-update-query", 7),
    DELETE_LOGIC ("k-delete-query", 11)

;

    private final String messageKey;
    private final int partition;

    public static int[] queryPartitionValues() {
        return Stream.of(PartitionQueryTypeEnum.values())
                .mapToInt(PartitionQueryTypeEnum::getPartition)
                .toArray();
    }

}
