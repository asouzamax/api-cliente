package br.com.builders.api.cliente.entity.jpa;

import java.io.Serializable;
import javax.persistence.*;

import br.com.builders.domain.enums.StatusEnum;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class ClienteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String uuid;
    private String nome;

    @Column(name = "ultimonome")
    private String ultimoNome;
    private Integer idade;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
