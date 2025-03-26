package org.solar.system.mdm.service.base;

import jakarta.persistence.EntityManager;
import org.apache.commons.collections4.CollectionUtils;
import org.solar.system.mdm.model.base.AbstractCommonEntity;
import org.solar.system.mdm.model.mapper.GlobalMapper;
import org.solar.system.mdm.service.config.CacheComponentUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Objects;

public abstract class AbstractService extends AbstractCommon {

	@Autowired
	protected CacheComponentUtils cacheComponentUtils;

	@Autowired
	protected GlobalMapper mapper;

	@Autowired
	protected EntityManager entityManager;

	public static final String FACTORY_JSON_AS_OBJECT = "kafkaJsonAsObjectListenerContainerFactory";
	public static final String RETRY_FACTORY_JSON_AS_OBJECT = "retryKafkaJsonAsObjectListenerContainerFactory";

	protected void saveEntityInCache(AbstractCommonEntity entity, String cacheKey, String cacheName, boolean useCache) {

		if(Objects.isNull(entity))
			return;
		addToCache(entity, cacheKey, cacheName, useCache);

	}

	protected void saveObjectInCache(Object entity, String cacheKey, String cacheName, boolean useCache) {

		if(Objects.isNull(entity))
			return;
		addToCache(entity, cacheKey, cacheName, useCache);

	}

	protected void saveListEntitiesInCache(Collection<? extends AbstractCommonEntity> entities, String cacheKey, String cacheName, boolean useCache) {

		if(CollectionUtils.isEmpty(entities))
			return;
		addToCache(entities, cacheKey, cacheName, useCache);

	}

	private void addToCache(Object entities, String cacheKey, String cacheName, boolean useCache) {
		try {
			cacheComponentUtils.addToCache(cacheKey, cacheName, entities, useCache);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	protected <T extends AbstractCommonEntity> T getEntityFromCache(Class<? extends AbstractCommonEntity> clazz, final String cacheKey,
																	final String cacheName, boolean useCache) {
		AbstractCommonEntity abstractEntity = null;
		try {
			abstractEntity = cacheComponentUtils.get(cacheKey, cacheName, clazz, useCache);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		}
		return (T) abstractEntity;
	}

	protected <T> T getObjectFromCache(Class<T> clazz, final String cacheKey, final String cacheName, boolean useCache) {
		T object = null;
		try {
			object = cacheComponentUtils.get(cacheKey, cacheName, clazz, useCache);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		}
		return (T) object;
	}

	@SuppressWarnings("unchecked")
	protected <T extends AbstractCommonEntity> Collection<T> getListEntitiesFromCache(Class<? extends AbstractCommonEntity> clazz,
																						final String cacheKey,
																						final String cacheName,
																						boolean useCache) {
		Collection<AbstractCommonEntity> abstractEntity = null;
		try {
			abstractEntity = cacheComponentUtils.getList(cacheKey, cacheName, clazz, useCache);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		}
		return (Collection<T>) abstractEntity;
	}

	protected void evictCacheKeyFromCache(final String cacheKey,
										  final String cacheName,
										  Class<? extends AbstractCommonEntity> clazz) {
		try {
			cacheComponentUtils.evictCacheKey(cacheKey, cacheName, clazz);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		}
	}

}
