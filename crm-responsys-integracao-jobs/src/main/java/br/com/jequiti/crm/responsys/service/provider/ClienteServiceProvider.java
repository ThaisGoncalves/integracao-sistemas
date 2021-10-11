package br.com.jequiti.crm.responsys.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.model.sql.Cliente;
import br.com.jequiti.crm.responsys.repository.ClienteRepository;
import br.com.jequiti.crm.responsys.service.ClienteService;

@Service
public class ClienteServiceProvider implements ClienteService {

	@Autowired
	private ClienteRepository _clienteRepository;

	@Override
	public Cliente buscarClientePorId(Long idCliente) throws Exception {

		return this._clienteRepository.findByIdCliente(idCliente);
	}
}
