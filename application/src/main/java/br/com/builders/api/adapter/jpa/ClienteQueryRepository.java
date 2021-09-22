package br.com.builders.api.adapter.jpa;

import br.com.builders.api.entity.jpa.ClienteEntity;
import br.com.builders.api.entity.jpa.JpaContext;
import br.com.builders.api.entity.mapper.ClienteRestMapper;
import br.com.builders.domain.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "clienteQueryRepository")
public interface ClienteQueryRepository extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByUuid(String id);

    Page<ClienteEntity> findByNomeContaining(String nome, Pageable pageable);
}
