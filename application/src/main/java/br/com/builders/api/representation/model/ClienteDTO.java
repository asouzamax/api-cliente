package br.com.builders.api.representation.model;


import br.com.builders.api.representation.BaseDTO;
import br.com.builders.domain.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(min = 3, max = 128)
	private String nome;
	@NotEmpty
	private String ultimoNome;
	@NotEmpty
	private String idade;

	private StatusEnum status;
	
}
