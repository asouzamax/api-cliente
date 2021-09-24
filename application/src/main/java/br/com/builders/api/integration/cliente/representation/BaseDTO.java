package br.com.builders.api.integration.cliente.representation;

import br.com.builders.api.integration.cliente.handle.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDTO {

    @NotBlank(groups = ValidationGroups.Id.class)
    @ApiModelProperty(value = "Identificador único")
    private String uuid;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(value = "Data de cadastro")
    private OffsetDateTime data;
}
