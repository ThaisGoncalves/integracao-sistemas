package br.com.jequiti.crm.responsys.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.jequiti.crm.responsys.service.SqlIntegracaoClienteCrmService;
import br.com.jequiti.crm.responsys.service.SqlIntegracaoCrmPedidoService;

@Controller
public class IntegracaoCrmResponsysJobsController {

	private static final Logger logger = LoggerFactory.getLogger(IntegracaoCrmResponsysJobsController.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private SqlIntegracaoClienteCrmService _sqlIntegracaoClienteCrmService;

	@Autowired
	private SqlIntegracaoCrmPedidoService _sqlIntegracaoCrmPedidoService;

	/**
	 * Método responsável pelo serviço de envio de dados para o CRM. Será executado
	 * por task.
	 * 
	 * @throws Exception
	 */
	public void integrarDadosCrm() throws Exception {

		logger.info("#===>> INÍCIO DO SERVIÇO DE INTEGRAÇAO CRM - CLIENTE {} <<===#", dateFormat.format(new Date()));

		this._sqlIntegracaoClienteCrmService.integrar();

		logger.info("#===>> FIM DO SERVIÇO DE INTEGRAÇAO CRM - CLIENTE {}<<===#", dateFormat.format(new Date()));

		logger.info("#===>> INÍCIO DO SERVIÇO DE INTEGRAÇAO CRM - PEDIDO {} <<===#", dateFormat.format(new Date()));

		this._sqlIntegracaoCrmPedidoService.integrar();

		logger.info("#===>> FIM DO SERVIÇO DE INTEGRAÇAO CRM - PEDIDO {} <<===#", dateFormat.format(new Date()));
	}
}
