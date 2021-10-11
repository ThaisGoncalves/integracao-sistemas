package br.com.jequiti.crm.responsys.service.provider;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.enums.TipoEvento;
import br.com.jequiti.crm.responsys.model.crm.ListName;
import br.com.jequiti.crm.responsys.model.crm.MergeProfileExtensionRecipients;
import br.com.jequiti.crm.responsys.model.crm.Requisicao;
import br.com.jequiti.crm.responsys.model.sql.Cliente;
import br.com.jequiti.crm.responsys.model.sql.ClienteAtributo;
import br.com.jequiti.crm.responsys.model.sql.Evento;
import br.com.jequiti.crm.responsys.model.sql.Pedido;
import br.com.jequiti.crm.responsys.service.ClienteAtributoService;
import br.com.jequiti.crm.responsys.service.ClienteService;
import br.com.jequiti.crm.responsys.service.CrmAuthenticationRestService;
import br.com.jequiti.crm.responsys.service.EventoService;
import br.com.jequiti.crm.responsys.service.LayoutEventoService;
import br.com.jequiti.crm.responsys.service.LayoutIntegracaoPedidoService;
import br.com.jequiti.crm.responsys.service.PedidoService;
import br.com.jequiti.crm.responsys.service.RequisicaoService;
import br.com.jequiti.crm.responsys.service.SqlIntegracaoCrmPedidoService;

@Service
public class SqlIntegracaoCrmPedidoServiceProvider implements SqlIntegracaoCrmPedidoService {

	@Autowired
	private Environment _environment;
	
	private static final Logger logger = LoggerFactory.getLogger(SqlIntegracaoClienteCrmServiceProvider.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private final String endPointIntegracao = this._environment.getProperty("crm.endPointIntegracao.pedido");
	private final String endPointEvento = this._environment.getProperty("crm.endPointEvento.pedido");

	@Autowired
	private CrmAuthenticationRestService _authenticationService;

	@Autowired
	private EventoService _eventoService;

	@Autowired
	private PedidoService _pedidoService;

	@Autowired
	private ClienteService _clienteService;

	@Autowired
	private ClienteAtributoService _clienteAtributoService;

	@Autowired
	private LayoutIntegracaoPedidoService _layoutIntegracaoPedidoService;

	@Autowired
	private LayoutEventoService _layoutEventoService;

	@Autowired
	private RequisicaoService _requisicaoService;

	public void integrar() {

		Pedido pedido = null;

		try {

			/**
			 * Carrega eventos da tabela crm_sis_eventos, elegíveis à serem integrados no
			 * crm.
			 */
			for (Evento evento : this._eventoService.buscarEventoPorTabelaOrigem("CRM_GOP_PEDIDO")) {

				pedido = this._pedidoService.buscarPedidoPorIdPedido(Long.valueOf(evento.getChaveTabela()));
				if (pedido != null) {

					ClienteAtributo riid = this._clienteAtributoService.buscarClienteAtributoPorIdClienteEnomeAtributo(
							Long.valueOf(pedido.getIdCliente()), "RIID_");

					if (riid != null) {

						/**
						 * Monta o layout de envio dos dados para integrar no CRM
						 */
						MergeProfileExtensionRecipients mergeProfileExtensionRecipients = this._layoutIntegracaoPedidoService
								.montar(pedido);

						/**
						 * Envia dados para o CRM.
						 */
						Requisicao requisicaoIntegracao = new Requisicao();
						requisicaoIntegracao
								.setUrl(this._authenticationService.authenticate().getEndPoint() + endPointIntegracao);
						requisicaoIntegracao.setToken(this._authenticationService.authenticate().getAuthToken());
						requisicaoIntegracao.setObjeto(mergeProfileExtensionRecipients);
						requisicaoIntegracao.setOperacao(TipoEvento.atualizar.getReferencia());

						String retornoRequisicao = this._requisicaoService.requerer(requisicaoIntegracao);
						if (retornoRequisicao != null) {

							/**
							 * Inclui do cliente na régua Consultora_Iniciante SEMPRE para os casos em que o
							 * PERFIL for igual a INICIANTE caso seja um INSERT
							 */
							if (evento.getTipoEvento().equals(TipoEvento.criar.getReferencia())) {

								ClienteAtributo perfilCliente = this._clienteAtributoService
										.buscarClienteAtributoPorIdClienteEnomeAtributo(
												Long.valueOf(pedido.getIdCliente()), "PERFIL");

								if (perfilCliente != null && perfilCliente.getValorAtributo().equals("INICIANTE")) {

									Cliente cliente = this._clienteService.buscarClientePorId(pedido.getIdCliente());

									ListName listNameEvento = new ListName();
									listNameEvento.setFolderName("Consultores_jequiti");
									listNameEvento.setObjectName("Consultores_Jequiti");

									Requisicao requisicaoEvento = new Requisicao();
									requisicaoEvento.setUrl(
											this._authenticationService.authenticate().getEndPoint() + endPointEvento);
									requisicaoEvento
											.setToken(this._authenticationService.authenticate().getAuthToken());
									requisicaoEvento
											.setObjeto(this._layoutEventoService.montar(cliente, listNameEvento));
									requisicaoEvento.setOperacao(TipoEvento.atualizar.getReferencia());

									if (this._requisicaoService.requerer(requisicaoEvento) == null) {

										throw new RuntimeException("Erro: Consultora iniciante, cliente id "
												+ cliente.getIdCliente()
												+ ", não incluída na régua por falha na requisição, devido dados inconsistentes ou incompletos");
									}
								}
							}

							/**
							 * Deleta o evento na tabela crm_sis_evento para o este pedido
							 */
							this._eventoService.deletar(evento);

						} else {

							throw new RuntimeException("Erro: integração do pedido id " + pedido.getIdPedido()
									+ ", operação " + evento.getTipoEvento()
									+ ", não foi integrado no CRM, devido dados inconsistentes ou incompletos");
						}

					} else {

						throw new RuntimeException("ERRO: integração do pedido id " + pedido.getIdPedido()
								+ ", operação " + evento.getTipoEvento()
								+ ", não foi integrado no CRM, devido o cliente não estar cadastrado no CRM");
					}
				}
			}

		} catch (Exception e) {
			logger.error(
					"ERRO: integção do pedido id " + pedido.getIdPedido() + ", método "
							+ Thread.currentThread().getStackTrace()[1].getMethodName() + " da classe "
							+ Thread.currentThread().getStackTrace()[1].getClassName() + ": " + e.toString(),
					dateFormat.format(new Date()));
		}
	}
}
