package br.com.builders.api.integration.cliente.infraestructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseMessage {

    private String mensagem;
    private String mensagemTecnica;
    private Object body;
}