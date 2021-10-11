package br.com.jequiti.crm.responsys.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.model.sql.Contato;
import br.com.jequiti.crm.responsys.repository.ContatoRepository;
import br.com.jequiti.crm.responsys.service.ContatoService;

@Service
public class ContatoServiceProvider implements ContatoService {

	@Autowired
	private ContatoRepository _contatoRepository;

	@Override
	public Contato buscarContatoPorIdClienteEtipo(Long idCliente, int tipo) throws Exception {

		return this._contatoRepository.findByIdClienteAndTipo(idCliente, tipo);
	}
}
