package br.com.builders.domain.entity;

import br.com.builders.domain.enums.StatusEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uuid;
	private String nome;
	private String ultimoNome;
	private Integer idade;
	private StatusEnum status;

}
