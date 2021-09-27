package br.com.builders.domain.usercase;

import br.com.builders.domain.entity.Cliente;
import br.com.builders.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.time.LocalDate;

@Named(value = "atualizarClienteService")
@RequiredArgsConstructor
public class AtualizarClienteService {
	
	private final ClienteRepository clienteRepository;

	public Cliente execute(final Cliente cliente) {
		cliente.setData (LocalDate.now ());
		return this.clienteRepository.save(cliente);
	}
	
}
