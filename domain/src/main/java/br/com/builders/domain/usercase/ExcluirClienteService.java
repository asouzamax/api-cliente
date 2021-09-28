package br.com.builders.domain.usercase;

import br.com.builders.domain.entity.Cliente;
import br.com.builders.domain.entity.IdGenerator;
import br.com.builders.domain.enums.StatusEnum;
import br.com.builders.domain.exception.ClienteJaCadastradoException;
import br.com.builders.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named(value = "excluirClienteService")
@RequiredArgsConstructor
public class ExcluirClienteService {
	
	private final ClienteRepository clienteRepository;

	public void execute(final Cliente cliente) {
		this.clienteRepository.delete (cliente);
	}
	
}
