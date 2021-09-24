package br.com.builders.domain.repository;

import br.com.builders.domain.entity.BaseEntity;
import br.com.builders.domain.entity.Cliente;

public interface Repository<T extends BaseEntity> {

    Cliente save(Cliente t);
    void delete(Cliente t);

}
