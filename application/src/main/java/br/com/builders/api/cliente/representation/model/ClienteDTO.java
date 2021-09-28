package br.com.builders.api.cliente.representation.model;


import br.com.builders.api.cliente.representation.BaseDTO;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ClienteDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(min = 3, max = 128)
	private String nome;

	@NotNull
	private String ultimoNome;
	@NotNull
	private Integer idade;

}
