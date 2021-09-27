package br.com.builders.api.cliente.adapter.jpa;

import br.com.builders.api.cliente.entity.jpa.ClienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "clienteQueryRepository")
public interface ClienteQueryRepository extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByUuid(String id);

    Page<ClienteEntity> findByNomeContaining(String nome, Pageable pageable);
}
