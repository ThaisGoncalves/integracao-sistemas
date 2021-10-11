package br.com.jequiti.crm.responsys.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.model.sql.Pedido;
import br.com.jequiti.crm.responsys.repository.PedidoRepository;
import br.com.jequiti.crm.responsys.service.PedidoService;

@Service
public class PedidoServiceProvider implements PedidoService {

	@Autowired
	private PedidoRepository _pedidoRepository;

	@Override
	public Pedido buscarPedidoPorIdPedido(Long idPedido) throws Exception {
		
		return this._pedidoRepository.findByIdPedido(idPedido);
	}
}
