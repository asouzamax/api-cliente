package br.com.builders.domain.repository;

import br.com.builders.domain.entity.BaseEntity;

public interface Repository<T extends BaseEntity> {
	T save(T t);
	void delete(T t);

}
