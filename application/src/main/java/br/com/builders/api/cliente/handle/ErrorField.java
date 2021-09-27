package br.com.builders.api.cliente.handle;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorField {

  private final String nome;

  private final String descricao;

}
