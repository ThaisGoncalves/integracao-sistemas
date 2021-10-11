package br.com.jequiti.crm.responsys.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.model.sql.PedidoAtributo;
import br.com.jequiti.crm.responsys.repository.PedidoAtributoRepository;
import br.com.jequiti.crm.responsys.service.PedidoAtributoService;

@Service
public class PedidoAtributoServiceProvider implements PedidoAtributoService {

	@Autowired
	private PedidoAtributoRepository _pedidoAtributoRepository;

	@Override
	public PedidoAtributo buscarPedidoAtributoPorIdClienteEnomeAtributo(Long idPedido, String nomeAtributo)
			throws Exception {

		return this._pedidoAtributoRepository.findByIdPedidoAndNomeAtributo(idPedido, nomeAtributo);
	}
}
