package br.com.builders.domain.exception;

public class ClienteJaCadastradoException extends BusinessException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ClienteJaCadastradoException(String nome) {
        super("Cliente já está cadastrado: " + nome);
    }
}
