package br.com.jequiti.crm.responsys.service;

import br.com.jequiti.crm.responsys.model.crm.MergeProfileExtensionRecipients;
import br.com.jequiti.crm.responsys.model.sql.Pedido;

public interface LayoutIntegracaoPedidoService {

	public MergeProfileExtensionRecipients montar(Pedido pedido);
}
