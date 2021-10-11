package br.com.jequiti.crm.responsys.service;

import br.com.jequiti.crm.responsys.model.sql.ClienteAtributo;

public interface ClienteAtributoService {

	public ClienteAtributo salvar(ClienteAtributo clienteAtributo) throws Exception;

	public ClienteAtributo buscarClienteAtributoPorIdClienteEnomeAtributo(Long idCliente, String nomeAtributo)
			throws Exception;
}
