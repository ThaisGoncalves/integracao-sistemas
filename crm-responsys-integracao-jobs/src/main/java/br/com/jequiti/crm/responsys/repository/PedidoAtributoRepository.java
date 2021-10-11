package br.com.jequiti.crm.responsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jequiti.crm.responsys.model.sql.PedidoAtributo;

@Repository
public interface PedidoAtributoRepository extends JpaRepository<PedidoAtributo, Long> {

	public PedidoAtributo findByIdPedidoAndNomeAtributo(Long idPedido, String nomeAtributo);
}
