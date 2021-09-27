package br.com.builders.api.cliente.rest;

import javax.validation.Valid;

import br.com.builders.api.cliente.adapter.jpa.ClienteQueryRepository;
import br.com.builders.api.cliente.entity.jpa.ClienteEntity;
import br.com.builders.api.cliente.entity.mapper.ClienteRestMapper;
import br.com.builders.domain.entity.Cliente;
import br.com.builders.api.cliente.exception.ClienteNaoEncontradoHttpException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.builders.service.ClienteService;
import br.com.builders.api.cliente.representation.model.ClienteDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = { "Operações de cliente" })
@RestController
@RequestMapping("cliente")
@RequiredArgsConstructor
public class ClienteRest extends MapperRest<Cliente, ClienteDTO> {

	private final ClienteService clienteService;
	private final ClienteQueryRepository clienteQueryRepository;

	@ApiOperation(value = "Cria um cliente")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cria e retorna um cliente", response = ClienteDTO.class),
			@ApiResponse(code = 400, message = "Erros negociais: validações da dados e fluxo", response = Error.class),
			@ApiResponse(code = 500, message = "Erros não esperados") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteDTO salvar(@Valid @RequestBody ClienteDTO clienteDTO) {
		return fromModel (this.clienteService.criarCliente(toModel (clienteDTO)));
	}

	@ApiOperation(value = "Retorna um cliente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna um cliente", response = ClienteDTO.class),
			@ApiResponse(code = 400, message = "Erros negociais: validações da dados e fluxo", response = Error.class),
			@ApiResponse(code = 500, message = "Erros não experados")
	})
	@GetMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public ClienteEntity readOne(@PathVariable("id") String id) {
		return findClienteByUuid (id);
	}
	
	@ApiOperation(value = "Atualiza um cliente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ator atualizado", response = ClienteDTO.class),
			@ApiResponse(code = 400, message = "Erros negociais: validações da dados e fluxo", response = Error.class),
			@ApiResponse(code = 500, message = "Erros não experados")
	})
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public ClienteDTO update(@Valid @RequestBody ClienteDTO cliente) {
		findClienteByUuid (cliente.getUuid ());
		return fromModel(clienteService.atualizarCliente(toModel(cliente)));
	}

	@ApiOperation(value = "Deleta um cliente")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Resposta sem conteúdo"),
			@ApiResponse(code = 400, message = "Erros negociais: validações da dados e fluxo", response = Error.class),
			@ApiResponse(code = 500, message = "Erros não experados")
	})
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		final var clienteEncontrado = findClienteByUuid (id);
		clienteService.excluirCliente(ClienteRestMapper.INSTANCE.ClienteDBtoCliente (clienteEncontrado));
	}

	@ApiOperation(value = "Retorna a listagem de clientes")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a lista de clientes", response = ClienteEntity[].class),
			@ApiResponse(code = 400, message = "Erros negociais: validações da dados e fluxo", response = Error.class),
			@ApiResponse(code = 500, message = "Erros não experados")
	})
	@GetMapping("/clientes")
	public ResponseEntity<Map<String, Object>> getAllClientes(
			@RequestParam(required = false) String nome,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
	) {

		try {
			List<ClienteEntity> clientes = new ArrayList<ClienteEntity> ();
			Pageable paging = PageRequest.of(page, size);

			Page<ClienteEntity> pageClientes;
			if (nome == null)
				pageClientes = clienteQueryRepository.findAll(paging);
			else
				pageClientes = clienteQueryRepository.findByNomeContaining (nome, paging);

			clientes = pageClientes.getContent();

			Map<String, Object> response = new HashMap<> ();
			response.put("clientes", clientes);
			response.put("currentPage", pageClientes.getNumber());
			response.put("totalItems", pageClientes.getTotalElements());
			response.put("totalPages", pageClientes.getTotalPages());

			return new ResponseEntity<> (response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ClienteEntity findClienteByUuid(String uuid){
		return clienteQueryRepository.findByUuid(uuid).orElseThrow(() -> new ClienteNaoEncontradoHttpException ());
	}
}