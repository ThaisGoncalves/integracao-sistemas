package br.com.jequiti.crm.responsys.service;

import java.util.List;

import br.com.jequiti.crm.responsys.model.sql.PedidoLinha;

public interface PedidoLinhaService {

	public List<PedidoLinha> buscarPedidoLinhaPorCodigoPedido(Long idPedido) throws Exception;
}
