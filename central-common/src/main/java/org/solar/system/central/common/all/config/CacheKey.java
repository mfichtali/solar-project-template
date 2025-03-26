package org.solar.system.central.common.all.config;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(of = { "key", "className" })
public class CacheKey {

    private String key;
    private String className;

}
