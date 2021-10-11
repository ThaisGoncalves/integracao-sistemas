package br.com.jequiti.crm.responsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jequiti.crm.responsys.model.sql.PedidoLinha;

@Repository
public interface PedidoLinhaRepository extends JpaRepository<PedidoLinha, Long> {

	public List<PedidoLinha> findByIdPedido(Long idPedido);
}
