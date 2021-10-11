package br.com.jequiti.crm.responsys.service;

import br.com.jequiti.crm.responsys.model.sql.Pedido;

public interface PedidoService {

	public Pedido buscarPedidoPorIdPedido(Long idPedido) throws Exception;
}
