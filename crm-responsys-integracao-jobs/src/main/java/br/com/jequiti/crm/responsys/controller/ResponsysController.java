package br.com.jequiti.crm.responsys.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jequiti.crm.responsys.model.crm.Authentication;
import br.com.jequiti.crm.responsys.service.CrmAuthenticationRestService;
import br.com.jequiti.crm.responsys.service.SqlIntegracaoClienteCrmService;
import br.com.jequiti.crm.responsys.service.SqlIntegracaoCrmPedidoService;

@RestController
public class ResponsysController {

	@Autowired
	private CrmAuthenticationRestService _authenticationService;

	@Autowired
	private SqlIntegracaoClienteCrmService _sqlIntegracaoClienteCrmService;

	@Autowired
	private SqlIntegracaoCrmPedidoService _sqlIntegracaoCrmPedidoService;

	/**
	 * Busca token.
	 * 
	 * @return
	 * @throws AuthenticationException
	 */
	@PostMapping("/crm/token")
	public ResponseEntity<Authentication> autenticar() {
		return new ResponseEntity<Authentication>(this._authenticationService.authenticate(), HttpStatus.OK);
	}

	/**
	 * Cria cadastro do cliente no CRM.
	 * 
	 * @return
	 * @throws AuthenticationException
	 */
	@PostMapping("/crm/cliente/integrar")
	public void criarClienteCrm() {
		this._sqlIntegracaoClienteCrmService.integrar();
	}

	/**
	 * Cria cadastro do pedido no CRM.
	 * 
	 * @return
	 * @throws AuthenticationException
	 */
	@PostMapping("/crm/pedido/integrar")
	public void criarPedidoCrm() {
		this._sqlIntegracaoCrmPedidoService.integrar();
	}
}
