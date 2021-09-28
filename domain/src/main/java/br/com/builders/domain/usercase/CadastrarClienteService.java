package br.com.builders.domain.usercase;

import javax.inject.Named;

import br.com.builders.domain.entity.Cliente;
import br.com.builders.domain.entity.IdGenerator;
import br.com.builders.domain.enums.StatusEnum;
import br.com.builders.domain.exception.ClienteJaCadastradoException;
import br.com.builders.domain.exception.ClienteValidationException;
import br.com.builders.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Named(value = "cadastrarClienteService")
@RequiredArgsConstructor
public class CadastrarClienteService {
	
	private final ClienteRepository clienteRepository;
	private final IdGenerator idGenercliente;

	public Cliente execute(final Cliente cliente) {
		Optional.ofNullable (cliente.getNome ())
				.orElseThrow (()-> new ClienteValidationException ("O nome do cliente não pode estar vazio."));

		if (clienteRepository.ehClienteComNomeJaExistente (cliente.getNome(), cliente.getUltimoNome ())) {
			throw new ClienteJaCadastradoException (cliente.getNome());
		}
		cliente.setUuid(idGenercliente.generate());
		cliente.setStatus (StatusEnum.ATIVO);
		return this.clienteRepository.save(cliente);
	}
	
}
