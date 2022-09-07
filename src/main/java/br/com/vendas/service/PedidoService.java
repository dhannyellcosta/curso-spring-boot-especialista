package br.com.vendas.service;

import br.com.vendas.domain.entity.Pedido;
import br.com.vendas.rest.dto.PedidoDTO;

public interface PedidoService {
	
	Pedido salvar(PedidoDTO dto);
}
