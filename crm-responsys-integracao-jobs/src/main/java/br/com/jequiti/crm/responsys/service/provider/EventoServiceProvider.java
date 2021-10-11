package br.com.jequiti.crm.responsys.service.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jequiti.crm.responsys.model.sql.Evento;
import br.com.jequiti.crm.responsys.repository.EventoRepository;
import br.com.jequiti.crm.responsys.service.EventoService;

@Service
public class EventoServiceProvider implements EventoService {

	@Autowired
	private EventoRepository _eventoRepository;

	@Override
	public List<Evento> buscarEventoPorTabelaOrigem(String tabelaOrigem) throws Exception {

		return this._eventoRepository.findByTabelaOrigem(tabelaOrigem);
	}

	@Override
	@Transactional
	public void deletar(Evento evento) throws Exception {

		this._eventoRepository.delete(evento);
	}
}
