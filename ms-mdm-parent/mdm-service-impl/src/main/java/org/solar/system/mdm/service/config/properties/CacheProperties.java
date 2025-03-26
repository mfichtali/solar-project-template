package org.solar.system.mdm.service.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "application.cache-mdm")
@RefreshScope
public class CacheProperties {

	private boolean cacheActive;

    private Map<String, String> cacheDataNames;

	private int cacheDataInitialCapacity;

	private int cacheDataSizeMax;

	private int cacheDataLifetimeMinute;
}
