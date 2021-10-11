package br.com.jequiti.crm.responsys.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.model.sql.Endereco;
import br.com.jequiti.crm.responsys.repository.EnderecoRepository;
import br.com.jequiti.crm.responsys.service.EnderecoService;

@Service
public class EnderecoServiceProvider implements EnderecoService {

	@Autowired
	private EnderecoRepository _enderecoRepository;

	@Override
	public Endereco buscaEnderecoPorIdClienteEtipo(Long idCliente, String tipo) throws Exception {
		
		return this._enderecoRepository.findByIdClienteAndTipo(idCliente, tipo);
	}
}
