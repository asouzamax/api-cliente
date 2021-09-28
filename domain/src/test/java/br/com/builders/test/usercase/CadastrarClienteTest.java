package br.com.builders.test.usercase;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import br.com.builders.domain.entity.Cliente;
import br.com.builders.domain.exception.ClienteJaCadastradoException;
import br.com.builders.domain.exception.ClienteValidationException;
import br.com.builders.domain.repository.ClienteRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.builders.domain.entity.IdGenerator;
import br.com.builders.domain.usercase.CadastrarClienteService;

public class CadastrarClienteTest {

	private CadastrarClienteService cadastrarClienteService;

	@Mock
	private ClienteRepository clienteRepository;
	@Mock
	private IdGenerator idGenerator;

	@BeforeEach
	private void init() {
		MockitoAnnotations.openMocks(this);
		this.cadastrarClienteService = new CadastrarClienteService (clienteRepository, idGenerator);
	}

	@Test
	public void deveCadastrarCliente() {
		Cliente cliente = createCliente();

		when(clienteRepository.save(cliente)).thenReturn(cliente);
		when(idGenerator.generate()).thenReturn(UUID.randomUUID().toString());

		cadastrarClienteService.execute(cliente);

		verify(clienteRepository).save(cliente);
	}

	@Test
	public void naoDeveCadastrarClienteSemNome() {
		Cliente cliente = createCliente();
		cliente.setNome (null);

		ClienteValidationException thrown = Assertions.assertThrows(ClienteValidationException.class,
				() -> cadastrarClienteService.execute(cliente));

		when(clienteRepository.save(Mockito.any())).thenThrow(RuntimeException.class);
		when(idGenerator.generate()).thenReturn(UUID.randomUUID().toString());

		assertTrue(thrown.getMessage().contains("O nome do cliente não pode estar vazio."));

	}

	private Cliente createCliente() {
		return Cliente.builder ().idade (30).nome ("Teste").ultimoNome ("Cliente").build();
	}

}
