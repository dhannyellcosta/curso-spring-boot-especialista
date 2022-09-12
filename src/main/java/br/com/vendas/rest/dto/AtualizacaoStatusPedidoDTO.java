package br.com.vendas.rest.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizacaoStatusPedidoDTO {

	@NotEmpty(message = "{campo.status.obrigatorio}")
	private String novoStatus;
}
