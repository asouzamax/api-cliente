package br.com.builders.domain.exception;

public class ClienteNaoEncontradoException extends BusinessException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ClienteNaoEncontradoException() {
        super("Cliente não encontrado");
    }
}
