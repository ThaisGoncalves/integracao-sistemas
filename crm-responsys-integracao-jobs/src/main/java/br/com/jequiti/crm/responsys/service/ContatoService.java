package br.com.jequiti.crm.responsys.service;

import br.com.jequiti.crm.responsys.model.sql.Contato;

public interface ContatoService {

	public Contato buscarContatoPorIdClienteEtipo(Long idCliente, int tipo) throws Exception;
}
