package br.com.builders.api.entity.mapper;

import br.com.builders.domain.entity.Cliente;
import br.com.builders.api.entity.jpa.JpaContext;
import br.com.builders.api.entity.jpa.ClienteEntity;
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
