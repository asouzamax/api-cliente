package br.com.builders.service;

import javax.inject.Named;

import br.com.builders.domain.entity.Cliente;
import br.com.builders.domain.usercase.AtualizarClienteService;
import br.com.builders.domain.usercase.CadastrarClienteService;
import br.com.builders.domain.usercase.ExcluirClienteService;
import lombok.RequiredArgsConstructor;

@Named(value = "clienteService")
@RequiredArgsConstructor
public class ClienteService {

	private final CadastrarClienteService cadastrarClienteService;
	private final AtualizarClienteService atualizarClienteService;
	private final ExcluirClienteService excluirClienteService;

	public Cliente criarCliente(Cliente cliente) {
		return cadastrarClienteService.execute(cliente);
	}

	public Cliente atualizarCliente(Cliente cliente) {
		return atualizarClienteService.execute(cliente);
	}

	public void excluirCliente(Cliente cliente) {
		excluirClienteService.execute(cliente);
	}
}
