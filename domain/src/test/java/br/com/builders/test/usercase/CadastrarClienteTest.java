package br.com.builders.test.usercase;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import br.com.builders.domain.entity.Cliente;
import br.com.builders.domain.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.builders.domain.entity.IdGenerator;
import br.com.builders.domain.usercase.CadastrarClienteService;

/**
 * Unit test for simple App.
 */
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
		Cliente Cliente = createCliente();

		when(clienteRepository.save(Cliente)).thenReturn(Cliente);
		when(idGenerator.generate()).thenReturn(UUID.randomUUID().toString());

		cadastrarClienteService.execute(Cliente);

		verify(clienteRepository).save(Cliente);
	}

	/*@Test
	public void naoDeveCadastrarClienteSemNome() {
		Cliente cliente = createCliente();
		cliente.

		ClienteValidationException thrown = Assertions.assertThrows(ClienteValidationException.class,
				() -> cadastrarClienteService.execute(cliente));

		when(clienteRepository.save(Mockito.any())).thenThrow(RuntimeException.class);
		when(idGenerator.generate()).thenReturn(UUID.randomUUID().toString());

		assertTrue(thrown.getMessage().contains("Deve haver pelo menos um item no Cliente."));

	}*/

	private Cliente createCliente() {
		Cliente cliente = Cliente.builder ().idade (30).nome ("Teste").ultimoNome ("Cliente").build();
		return cliente;
	}

}
