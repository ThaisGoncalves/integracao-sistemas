package br.com.jequiti.crm.responsys.service;

import br.com.jequiti.crm.responsys.model.sql.PedidoAtributo;

public interface PedidoAtributoService {

	public PedidoAtributo buscarPedidoAtributoPorIdClienteEnomeAtributo(Long idPedido, String nomeAtributo)
			throws Exception;
}
