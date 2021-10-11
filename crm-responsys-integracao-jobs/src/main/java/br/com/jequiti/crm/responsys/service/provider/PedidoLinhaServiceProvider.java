package br.com.jequiti.crm.responsys.service.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.model.sql.PedidoLinha;
import br.com.jequiti.crm.responsys.repository.PedidoLinhaRepository;
import br.com.jequiti.crm.responsys.service.PedidoLinhaService;

@Service
public class PedidoLinhaServiceProvider implements PedidoLinhaService {

	@Autowired
	private PedidoLinhaRepository _pedidoLinhaRepository;

	@Override
	public List<PedidoLinha> buscarPedidoLinhaPorCodigoPedido(Long idPedido) throws Exception {

		return _pedidoLinhaRepository.findByIdPedido(idPedido);
	}
}
