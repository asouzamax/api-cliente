package br.com.builders.domain.exception;

public class ClienteNaoEncontradoException extends BusinessException {

    public ClienteNaoEncontradoException() {
        super("Cliente não encontrado");
    }
}
