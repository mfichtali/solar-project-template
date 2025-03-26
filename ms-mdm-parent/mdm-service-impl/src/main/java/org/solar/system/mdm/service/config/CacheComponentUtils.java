package org.solar.system.mdm.service.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.solar.system.central.common.all.config.CacheKey;
import org.solar.system.mdm.service.config.properties.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.solar.system.central.common.all.utils.ConstantUtils.CACHE_PREFIX;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheComponentUtils {

	private static final String CACHE_SEPARATOR = "-";

	private final CaffeineCacheManager caffeineCacheManager;

	private final CacheProperties cacheProperties;

	public void addToCache(@NonNull String cacheKey,
						   String cacheName,
						   Object data, boolean useCache) {

		if (!useCache || //
				!cacheProperties.isCacheActive() || //
				StringUtils.isBlank(cacheKey) || //
				Objects.isNull(data)) {
			return;
		}
		CaffeineCache caffeine = getCache(cacheName, data.getClass().getSimpleName());
		CacheKey cache = CacheKey.builder().key(cacheKey).className(caffeine.getName()).build();
		caffeine.put(cache, data);

	}

	public <T> T get(@NonNull String cacheKey,
					 String cacheName,
					 Class<T> clazz, boolean useCache) {

		if (!useCache ||
				!cacheProperties.isCacheActive() ||
				StringUtils.isBlank(cacheKey) ||
				Objects.isNull(clazz)) {
			return null;
		}
		CaffeineCache caffeine = getCache(cacheName, clazz.getSimpleName());
		CacheKey cache = CacheKey.builder().key(cacheKey).className(caffeine.getName()).build();
		Cache.ValueWrapper value = caffeine.get(cache);
		return value != null ? (T) value.get() : null;

	}

	public <T> Collection<T> getList(@NonNull String cacheKey,
								String cacheName,
								Class<?> clazz, boolean useCache) {

		if (!useCache ||
				!cacheProperties.isCacheActive() ||
				StringUtils.isBlank(cacheKey) ||
				Objects.isNull(clazz)) {
			return null;
		}
		CaffeineCache caffeine = getCache(cacheName, clazz.getSimpleName());
		CacheKey cache = CacheKey.builder().key(cacheKey).className(caffeine.getName()).build();
		Cache.ValueWrapper value = caffeine.get(cache);
		return value != null ? (Collection<T>) value.get() : null;

	}

	public boolean evictCacheByName(String cacheName) {
		Cache cache = caffeineCacheManager.getCache(cacheName);
		Assert.notNull(cache, String.format("A cache [%s] does not exist.", cacheName));
		cache.clear();
		return Boolean.TRUE;
	}

	public boolean evictAllCaches() {
		caffeineCacheManager.getCacheNames().forEach(this::evictCacheByName);
		return Boolean.TRUE;
	}

	public <T> void evictCacheKey(String cacheKey, String cacheName, Class<T> clazz) {
		Cache caffeine = getCache(cacheName, clazz.getSimpleName());
		CacheKey cache = CacheKey.builder().key(cacheKey).className(caffeine.getName()).build();
		caffeine.evictIfPresent(cache);
	}

	public List<String> cacheNames() {
		return new ArrayList<>(cacheProperties.getCacheDataNames().values());
	}

	private CaffeineCache getCache(String cacheKey, String className) {
		String name = StringUtils.isBlank(cacheKey) ?
				CACHE_PREFIX.concat(className) :
				cacheKey;
		if(!cacheProperties.getCacheDataNames().containsKey(name)) {
			throw new IllegalArgumentException(String.format("A cache name [%s] does not handle", name));
		}
		String cacheName = cacheProperties.getCacheDataNames().get(name);
		CaffeineCache cache = (CaffeineCache)  caffeineCacheManager.getCache(cacheName);
		Assert.notNull(cache, String.format("A cache [%s] does not exist", cacheName));
		return cache;
	}

}
