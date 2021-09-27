package br.com.builders.domain.usercase;

import javax.inject.Named;

import br.com.builders.domain.entity.Cliente;
import br.com.builders.domain.entity.IdGenerator;
import br.com.builders.domain.enums.StatusEnum;
import br.com.builders.api.cliente.exception.ClienteJaCadastradoException;
import br.com.builders.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Named(value = "cadastrarClienteService")
@RequiredArgsConstructor
public class CadastrarClienteService {
	
	private final ClienteRepository clienteRepository;
	private final IdGenerator idGenercliente;

	public Cliente execute(final Cliente cliente) {
		if (clienteRepository.ehClienteComNomeJaExistente (cliente.getNome(), cliente.getUltimoNome ())) {
			throw new ClienteJaCadastradoException (cliente.getNome());
		}
		cliente.setUuid(idGenercliente.generate());
		cliente.setStatus (StatusEnum.ATIVO);
		return this.clienteRepository.save(cliente);
	}
	
}
