package br.com.jequiti.crm.responsys.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.model.sql.ClienteAtributo;
import br.com.jequiti.crm.responsys.repository.ClienteAtributoRepository;
import br.com.jequiti.crm.responsys.service.ClienteAtributoService;

@Service
public class ClienteAtributoServiceProvider implements ClienteAtributoService {

	@Autowired
	private ClienteAtributoRepository _clienteAtributoRepository;

	@Override
	public ClienteAtributo salvar(ClienteAtributo clienteAtributo) throws Exception {

		return this._clienteAtributoRepository.save(clienteAtributo);
	}

	@Override
	public ClienteAtributo buscarClienteAtributoPorIdClienteEnomeAtributo(Long idCliente, String nomeAtributo)
			throws Exception {

		return this._clienteAtributoRepository.findByIdClienteAndNomeAtributo(idCliente, nomeAtributo);
	}
}
