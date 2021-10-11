package br.com.jequiti.crm.responsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jequiti.crm.responsys.model.sql.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	public List<Pedido> findByIdCliente(Long idCliente);

	public Pedido findByIdPedido(Long idPedido);
}
