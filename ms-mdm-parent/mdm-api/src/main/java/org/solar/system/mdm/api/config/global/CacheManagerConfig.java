package org.solar.system.mdm.api.config.global;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.solar.system.mdm.service.config.properties.CacheProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
@RequiredArgsConstructor
public class CacheManagerConfig {

	private final CacheProperties cacheProperties;

	@Bean
	Caffeine caffeineConfig() {
		return Caffeine.newBuilder() //
				.initialCapacity(cacheProperties.getCacheDataInitialCapacity()) //
				.maximumSize(cacheProperties.getCacheDataSizeMax()) //
				.recordStats() //
				.expireAfterWrite(cacheProperties.getCacheDataLifetimeMinute(), TimeUnit.MINUTES);
	}

	@Bean
	CaffeineCacheManager cacheManager(Caffeine caffeine) {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setCacheNames(cacheNames());
		caffeineCacheManager.setCaffeine(caffeine);
		return caffeineCacheManager;
	}

	public List<String> cacheNames() {
		return new ArrayList<>(cacheProperties.getCacheDataNames().values());
	}

}
