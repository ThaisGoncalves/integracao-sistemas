package br.com.jequiti.crm.responsys.service;

import br.com.jequiti.crm.responsys.model.sql.Pais;

public interface PaisService {

	public Pais buscarPaisPorCodPais(String codPais) throws Exception;
}
