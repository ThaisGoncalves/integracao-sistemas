package br.com.jequiti.crm.responsys.service.provider;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jequiti.crm.responsys.enums.TipoEvento;
import br.com.jequiti.crm.responsys.model.crm.ListName;
import br.com.jequiti.crm.responsys.model.crm.MergeListRecipients;
import br.com.jequiti.crm.responsys.model.crm.Requisicao;
import br.com.jequiti.crm.responsys.model.crm.RetornoEvento;
import br.com.jequiti.crm.responsys.model.sql.Cliente;
import br.com.jequiti.crm.responsys.model.sql.ClienteAtributo;
import br.com.jequiti.crm.responsys.model.sql.Evento;
import br.com.jequiti.crm.responsys.service.ClienteAtributoService;
import br.com.jequiti.crm.responsys.service.ClienteService;
import br.com.jequiti.crm.responsys.service.CrmAuthenticationRestService;
import br.com.jequiti.crm.responsys.service.EventoService;
import br.com.jequiti.crm.responsys.service.LayoutIntegracaoClienteService;
import br.com.jequiti.crm.responsys.service.RequisicaoService;
import br.com.jequiti.crm.responsys.service.SqlIntegracaoClienteCrmService;

@Service
public class SqlIntegracaoClienteCrmServiceProvider implements SqlIntegracaoClienteCrmService {

	@Autowired
	private Environment _environment;
	
	private static final Logger logger = LoggerFactory.getLogger(SqlIntegracaoClienteCrmServiceProvider.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private final String endPointIntegracao = this._environment.getProperty("crm.endPointIntegracao.cliente");
	private final String endPointEvento = this._environment.getProperty("crm.endPointEvento.cliente");

	@Autowired
	private CrmAuthenticationRestService _crmAuthenticationService;

	@Autowired
	private EventoService _eventoService;

	@Autowired
	private ClienteService _clienteService;

	@Autowired
	private ClienteAtributoService _clienteAtributoService;

	@Autowired
	private LayoutIntegracaoClienteService _layoutIntegracaoClienteService;

	@Autowired
	private LayoutEventoServiceProvider _layoutEventoServiceImpl;

	@Autowired
	private RequisicaoService _requisicaoService;

	public void integrar() {

		Cliente cliente = null;

		try {

			/**
			 * Carrega eventos da tabela crm_sis_eventos, elegíveis à serem integrados no
			 * crm.
			 */
			for (Evento evento : this._eventoService.buscarEventoPorTabelaOrigem("CRM_CAD_CLIENTE")) {

				cliente = this._clienteService.buscarClientePorId(Long.valueOf(evento.getChaveTabela()));
				if (cliente != null) {

					/**
					 * Verifica se no caso de cadastro, o cliente possui RIID_ cadastrado em
					 * atributos para evitar que o cadastro duplique, e se na atualização, o cliente
					 * possui RIID_ cadastrado em atributos, para evitar falha na atualizaçaõ
					 */
					if ((this._clienteAtributoService
							.buscarClienteAtributoPorIdClienteEnomeAtributo(cliente.getIdCliente(), "RIID_") == null
							&& evento.getTipoEvento() != TipoEvento.criar.getReferencia())

							|| this._clienteAtributoService.buscarClienteAtributoPorIdClienteEnomeAtributo(
									cliente.getIdCliente(), "RIID_") != null
									&& evento.getTipoEvento().equals(TipoEvento.atualizar.getReferencia())) {

						/**
						 * Monta o layout de envio dos dados para integrar no CRM
						 */
						MergeListRecipients mergeListrecipients = this._layoutIntegracaoClienteService.montar(cliente);

						/**
						 * Envia dados para o CRM.
						 */
						Requisicao requisicaoIntegracao = new Requisicao();
						requisicaoIntegracao.setUrl(
								this._crmAuthenticationService.authenticate().getEndPoint() + endPointIntegracao);
						requisicaoIntegracao.setToken(this._crmAuthenticationService.authenticate().getAuthToken());
						requisicaoIntegracao.setObjeto(mergeListrecipients);
						requisicaoIntegracao.setOperacao(TipoEvento.atualizar.getReferencia());

						String retornoRequisicao = this._requisicaoService.requerer(requisicaoIntegracao);
						if (retornoRequisicao != null) {

							/**
							 * Gera dado "RIID_" de retorno do CRM no SQL, na tabela
							 * crm_cad_atributo_cliente caso seja um INSERT.
							 */
							if (evento.getTipoEvento().equals(TipoEvento.criar.getReferencia())) {

								/**
								 * Ativação do cadastro no CRM.
								 */
								ListName listNameEvento = new ListName();
								listNameEvento.setFolderName("Consultores_jequiti");
								listNameEvento.setObjectName("Consultores_Jequiti");

								Requisicao requisicaoEvento = new Requisicao();
								requisicaoEvento.setUrl(
										this._crmAuthenticationService.authenticate().getEndPoint() + endPointEvento);
								requisicaoEvento.setToken(this._crmAuthenticationService.authenticate().getAuthToken());
								requisicaoEvento
										.setObjeto(this._layoutEventoServiceImpl.montar(cliente, listNameEvento));
								requisicaoEvento.setOperacao(TipoEvento.atualizar.getReferencia());

								if (this.gerarRegistroRetornoCrm(this._requisicaoService.requerer(requisicaoEvento),
										cliente) != true) {

									throw new RuntimeException("Erro: integção do cliente id " + cliente.getIdCliente()
											+ ". O cliente não foi ativado no CRM, devido dados inconsistentes ou incompletos");
								}
							}

							/**
							 * Deleta o evento na tabela crm_sis_evento para o este cliente
							 */
							this._eventoService.deletar(evento);

						} else {

							throw new RuntimeException("Erro: integção do cliente id " + cliente.getIdCliente()
									+ ", operação " + evento.getTipoEvento()
									+ ", não foi integrado no CRM, devido dados inconsistentes ou incompletos");
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error(
					"ERRO: integção do cliente id " + cliente.getIdCliente() + ", método "
							+ Thread.currentThread().getStackTrace()[1].getMethodName() + " da classe "
							+ Thread.currentThread().getStackTrace()[1].getClassName() + ": " + e.toString(),
					dateFormat.format(new Date()));
		}
	}

	private Boolean gerarRegistroRetornoCrm(String retornoIntegracao, Cliente cliente) throws Exception {

		Boolean flag = false;
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonObjectRetornoIntegracao = new JSONObject(
				retornoIntegracao.replace("[", "").replace("\"", "").replace("]", ""));
		RetornoEvento retornoEvento = mapper.readValue(jsonObjectRetornoIntegracao.toString(), RetornoEvento.class);

		if (retornoEvento.getSuccess() == true) {
			ClienteAtributo clienteAtributo = new ClienteAtributo();
			clienteAtributo.setIdCliente(cliente.getIdCliente());
			clienteAtributo.setNomeAtributo("RIID_");
			clienteAtributo.setValorAtributo(retornoEvento.getRecipientId().toString());

			ClienteAtributo retornoClienteAtributo = this._clienteAtributoService.salvar(clienteAtributo);
			if (retornoClienteAtributo != null)
				flag = true;
		}

		return flag;
	}
}