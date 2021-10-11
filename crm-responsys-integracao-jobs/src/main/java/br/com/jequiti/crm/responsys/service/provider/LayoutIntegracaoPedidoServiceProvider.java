package br.com.jequiti.crm.responsys.service.provider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.enums.TipoContato;
import br.com.jequiti.crm.responsys.exception.RuntimeException;
import br.com.jequiti.crm.responsys.model.crm.MergeProfileExtensionRecipients;
import br.com.jequiti.crm.responsys.model.crm.RecordData;
import br.com.jequiti.crm.responsys.model.sql.Pedido;
import br.com.jequiti.crm.responsys.model.sql.PedidoAtributo;
import br.com.jequiti.crm.responsys.model.sql.PedidoLinha;
import br.com.jequiti.crm.responsys.service.ClienteAtributoService;
import br.com.jequiti.crm.responsys.service.ContatoService;
import br.com.jequiti.crm.responsys.service.LayoutIntegracaoPedidoService;
import br.com.jequiti.crm.responsys.service.PedidoAtributoService;
import br.com.jequiti.crm.responsys.service.PedidoLinhaService;

@Service
public class LayoutIntegracaoPedidoServiceProvider implements LayoutIntegracaoPedidoService {

	private static final Logger logger = LoggerFactory.getLogger(LayoutIntegracaoPedidoServiceProvider.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private static final int tamanhoVetor = 12;
	private static final int maximoCaracteresItensPedido = 4000;
	private int indice;

	@Autowired
	private ClienteAtributoService _clienteAtributoService;

	@Autowired
	private ContatoService _contatoService;

	@Autowired
	private PedidoLinhaService _pedidoLinhaService;

	@Autowired
	private PedidoAtributoService _pedidoAtributoService;

	@Override
	public MergeProfileExtensionRecipients montar(Pedido pedido) {

		MergeProfileExtensionRecipients retorno = null;

		try {

			/**
			 * Dados do fieldNames
			 */

			String[] fieldNames = new String[tamanhoVetor];
			indice = 0;

			fieldNames[indice++] = "RIID_";
			fieldNames[indice++] = "ORDERID";
			fieldNames[indice++] = "DATA_PEDIDO";
			fieldNames[indice++] = "EMAIL_ADDRESS";
			fieldNames[indice++] = "EMAIL_ADDRESS_";
			fieldNames[indice++] = "DATA_PREVISTA_ENTREGA";
			fieldNames[indice++] = "STATUS_PEDIDO";
			fieldNames[indice++] = "CODIGO_PEDIDO";
			fieldNames[indice++] = "TIPO_PAGAMENTO";
			fieldNames[indice++] = "TIPO_PRODUTOS";
			fieldNames[indice++] = "NUMERO_PEDIDO";
			fieldNames[indice++] = "ITENS_PEDIDO";

			/**
			 * Dados do records: Lista dos clientes elegíveis à serem criados no CRM.
			 */

			List<String[]> listaRecords = new ArrayList<String[]>();

			String[] records = new String[tamanhoVetor];
			indice = 0;

			records[indice++] = this._clienteAtributoService
					.buscarClienteAtributoPorIdClienteEnomeAtributo(pedido.getIdCliente(), "RIID_").getValorAtributo();

			records[indice++] = pedido.getCodPedido().toString();
			records[indice++] = pedido.getDataPedido().toString();

			records[indice++] = this._contatoService
					.buscarContatoPorIdClienteEtipo(pedido.getIdCliente(), TipoContato.email.getCodigo()).getValor();

			records[indice++] = this._contatoService
					.buscarContatoPorIdClienteEtipo(pedido.getIdCliente(), TipoContato.email.getCodigo()).getValor();

			records[indice++] = pedido.getDataEntrega().toString();
			records[indice++] = pedido.getStatusPedido();
			records[indice++] = pedido.getCodPedido().toString();

			records[indice++] = "A VISTA";

			PedidoAtributo tipoProdutos = this._pedidoAtributoService
					.buscarPedidoAtributoPorIdClienteEnomeAtributo(pedido.getIdPedido(), "TIPO_PRODUTOS");

			if (tipoProdutos == null) {

				throw new RuntimeException("Erro: na montagem do layout do pedido id " + pedido.getIdPedido()
						+ ". O atributo TIPO_PRODUTOS não localizado na tabela crm_gop_atributo_pedido");
			} else {

				records[indice++] = tipoProdutos.getValorAtributo();
			}

			PedidoAtributo numeroPedido = this._pedidoAtributoService
					.buscarPedidoAtributoPorIdClienteEnomeAtributo(pedido.getIdPedido(), "NUMERO_PEDIDO");

			if (numeroPedido == null) {

				throw new RuntimeException("Erro: na montagem do layout do pedido id " + pedido.getIdPedido()
						+ ". O atributo NUMERO_PEDIDO não localizado crm_gop_atributo_pedido");
			} else {

				records[indice++] = numeroPedido.getValorAtributo();
			}

			List<Object> jsonPedidoLinha = new ArrayList<Object>();
			for (PedidoLinha pedidoLinha : this._pedidoLinhaService
					.buscarPedidoLinhaPorCodigoPedido(pedido.getIdPedido())) {

				JSONObject jsonObjectPedidoLinha = new JSONObject();
				jsonObjectPedidoLinha.put("codigo", pedidoLinha.getCodigoProduto());
				jsonObjectPedidoLinha.put("descricao", pedidoLinha.getDescricao());
				jsonObjectPedidoLinha.put("quantidade", pedidoLinha.getQuantidade().toString());
				jsonPedidoLinha.add(jsonObjectPedidoLinha);
			}

			if (jsonPedidoLinha.toString().length() < maximoCaracteresItensPedido) {

				records[indice++] = jsonPedidoLinha.toString();

			} else {

				throw new RuntimeException("ERRO: pedido codigo " + pedido.getCodPedido() + " excedeu o limite de "
						+ maximoCaracteresItensPedido + " caracteres do CRM, para o atributo ITENS_PEDIDO");
			}

			listaRecords.add(records);

			/**
			 * Dados do recordData: alimentado com os atributos acima.
			 */

			RecordData recordData = new RecordData();
			recordData.setFieldNames(fieldNames);
			recordData.setRecords(listaRecords);
			recordData.setMapTemplateName("");

			/**
			 * Dados do AtivacaoCadastro: Classe principal, alimentada com os atributos
			 * acima.
			 */

			MergeProfileExtensionRecipients mergeProfileExtensionRecipients = new MergeProfileExtensionRecipients();
			mergeProfileExtensionRecipients.setRecordData(recordData);
			mergeProfileExtensionRecipients.setInsertOnNoMatch(true);
			mergeProfileExtensionRecipients.setUpdateOnMatch("REPLACE_ALL");
			mergeProfileExtensionRecipients.setMatchColumnName1("RIID");
			mergeProfileExtensionRecipients.setMatchColumnName2("EMAIL_ADDRESS");

			retorno = mergeProfileExtensionRecipients;

		} catch (Exception e) {
			logger.error(
					"ERRO: método " + Thread.currentThread().getStackTrace()[1].getMethodName() + " da classe "
							+ Thread.currentThread().getStackTrace()[1].getClassName() + ": " + e.toString(),
					dateFormat.format(new Date()));
		}

		return retorno;
	}
}
