package br.com.vendas.repository;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.domain.entity.Pedido;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	List<Pedido> findByCliente(Cliente cliente);
	
	@Query("select p from Pedido p left join fetch p.itens where p.id= :id")
	Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}
