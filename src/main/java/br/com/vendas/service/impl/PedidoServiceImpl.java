package br.com.vendas.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.domain.entity.ItemPedido;
import br.com.vendas.domain.entity.Pedido;
import br.com.vendas.domain.entity.Produto;
import br.com.vendas.exception.RegraNegocioException;
import br.com.vendas.repository.ClienteRepository;
import br.com.vendas.repository.ItemPedidoRepository;
import br.com.vendas.repository.PedidoRepository;
import br.com.vendas.repository.ProdutoRepository;
import br.com.vendas.rest.dto.ItemPedidoDTO;
import br.com.vendas.rest.dto.PedidoDTO;
import br.com.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

	private final PedidoRepository pedidoRepository;
	private final ClienteRepository clienteRepository;
	private final ProdutoRepository produtoRepository;
	private final ItemPedidoRepository itemPedidoRepository;

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO dto) {

		Integer idCliente = dto.getCliente();
		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new RegraNegocioException("Códidgo de cliente inválido"));

		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		
		List<ItemPedido> itensPedidos = converterItens(pedido, dto.getItens());
		pedidoRepository.save(pedido);
		itemPedidoRepository.saveAll(itensPedidos);
		pedido.setItens(itensPedidos);

		return pedido;
	}

	private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
		if (itens.isEmpty()) {
			throw new RegraNegocioException("Não é possível realizar um pedido sem itens");
		}

		return itens.stream().map(dto -> {
			Integer idProduto = dto.getProduto();
			Produto produto = produtoRepository.findById(idProduto)
					.orElseThrow(() -> new RegraNegocioException("Código de produto inválido " + idProduto));
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantidade(dto.getQuantidade());
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produto);
			return itemPedido;
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		
		return pedidoRepository.findByIdFetchItens(id);
	}
}
