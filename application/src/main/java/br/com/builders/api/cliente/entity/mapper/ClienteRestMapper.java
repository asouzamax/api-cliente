package br.com.builders.api.cliente.entity.mapper;

import br.com.builders.api.cliente.entity.jpa.ClienteEntity;
import br.com.builders.api.cliente.entity.jpa.JpaContext;
import br.com.builders.domain.entity.Cliente;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface ClienteRestMapper {
	ClienteRestMapper INSTANCE = Mappers.getMapper(ClienteRestMapper.class);
	
	Cliente ClienteDBtoCliente(ClienteEntity Clientedb);

	ClienteEntity ClienteToClienteDB(Cliente Cliente, @Context JpaContext ctx);

}
