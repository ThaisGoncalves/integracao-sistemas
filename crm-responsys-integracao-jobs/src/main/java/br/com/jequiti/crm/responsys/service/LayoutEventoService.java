package br.com.jequiti.crm.responsys.service;

import br.com.jequiti.crm.responsys.model.crm.Event;
import br.com.jequiti.crm.responsys.model.crm.ListName;
import br.com.jequiti.crm.responsys.model.sql.Cliente;

public interface LayoutEventoService {

	public Event montar(Cliente cliente, ListName listNameParametros);
}
