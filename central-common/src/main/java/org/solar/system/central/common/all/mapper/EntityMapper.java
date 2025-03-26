package org.solar.system.central.common.all.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Contract for a generic dto to entity mapper.
 * 
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public interface EntityMapper<D, E> {

	public E toEntity(D dto);

	public D toDto(E entity);

	public List<E> toEntity(List<D> dtoList);

	public Set<E> toEntity(Set<D> dtoList);

	public Collection<E> toEntity(Collection<D> dtoList);

	public List<D> toDto(List<E> entityList);

	public Set<D> toDto(Set<E> entityList);
}
