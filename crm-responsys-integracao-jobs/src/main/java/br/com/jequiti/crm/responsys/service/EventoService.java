package br.com.jequiti.crm.responsys.service;

import java.util.List;

import br.com.jequiti.crm.responsys.model.sql.Evento;

public interface EventoService {
	
	public List<Evento> buscarEventoPorTabelaOrigem(String tabelaOrigem) throws Exception;

	public void deletar(Evento evento) throws Exception;
}
