package br.com.builders.api.adapter.jpa;

import br.com.builders.api.entity.jpa.ClienteEntity;
import br.com.builders.api.entity.jpa.JpaContext;
import br.com.builders.api.entity.mapper.ClienteRestMapper;
import br.com.builders.domain.entity.Cliente;
import br.com.builders.domain.repository.ClienteRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository(value = "clienteCrudRepository")
public interface ClienteCrudRepository extends CrudRepository<ClienteEntity, Long>, ClienteRepository {

    @Override
    default Cliente save(Cliente Cliente) {
        return ClienteRestMapper.INSTANCE.ClienteDBtoCliente(save (getT (Cliente)));
    }

    @Override
    default void delete(Cliente cliente){
        delete (getT (cliente));
    }

    default ClienteEntity getT(Cliente cliente) {
        return ClienteRestMapper.INSTANCE.ClienteToClienteDB (cliente, JpaContext.getInstance ());
    }
}
