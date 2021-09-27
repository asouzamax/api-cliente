package br.com.builders.api.cliente.exception;

import org.springframework.http.HttpStatus;

public class ClienteNaoEncontradoHttpException extends BusinessHttpExcception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ClienteNaoEncontradoHttpException() {
        super(HttpStatus.NOT_FOUND, "Cliente não encontrado");
    }

}
