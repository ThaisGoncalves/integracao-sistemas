package br.com.jequiti.crm.responsys.service;

import br.com.jequiti.crm.responsys.model.sql.Endereco;

public interface EnderecoService {

	public Endereco buscaEnderecoPorIdClienteEtipo(Long idCliente, String tipo) throws Exception;
}
