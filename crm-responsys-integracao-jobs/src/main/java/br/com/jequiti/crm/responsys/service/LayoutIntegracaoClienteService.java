package br.com.jequiti.crm.responsys.service;

import br.com.jequiti.crm.responsys.model.crm.MergeListRecipients;
import br.com.jequiti.crm.responsys.model.sql.Cliente;

public interface LayoutIntegracaoClienteService {

	public MergeListRecipients montar(Cliente cliente);
}
