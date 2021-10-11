package br.com.jequiti.crm.responsys.service.provider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.enums.TipoContato;
import br.com.jequiti.crm.responsys.exception.RuntimeException;
import br.com.jequiti.crm.responsys.model.crm.MergeListRecipients;
import br.com.jequiti.crm.responsys.model.crm.MergeRule;
import br.com.jequiti.crm.responsys.model.crm.RecordData;
import br.com.jequiti.crm.responsys.model.sql.Cliente;
import br.com.jequiti.crm.responsys.model.sql.ClienteAtributo;
import br.com.jequiti.crm.responsys.model.sql.Contato;
import br.com.jequiti.crm.responsys.model.sql.Endereco;
import br.com.jequiti.crm.responsys.model.sql.Pais;
import br.com.jequiti.crm.responsys.service.ClienteAtributoService;
import br.com.jequiti.crm.responsys.service.ContatoService;
import br.com.jequiti.crm.responsys.service.EnderecoService;
import br.com.jequiti.crm.responsys.service.LayoutIntegracaoClienteService;
import br.com.jequiti.crm.responsys.service.PaisService;

@Service
public class LayoutIntegracaoClienteServiceProvider implements LayoutIntegracaoClienteService {

	private static final Logger logger = LoggerFactory.getLogger(LayoutIntegracaoClienteServiceProvider.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private static final int tamanhoVetor = 18;
	private int indice;

	@Autowired
	private ClienteAtributoService _clienteAtributoService;

	@Autowired
	private EnderecoService _enderecoService;

	@Autowired
	private ContatoService _contatoService;

	@Autowired
	private PaisService _paisService;

	@Override
	public MergeListRecipients montar(Cliente cliente) {

		MergeListRecipients retorno = null;

		try {

			/**
			 * Dados do fieldNames
			 */

			String[] fieldNames = new String[tamanhoVetor];
			indice = 0;
			fieldNames[indice++] = "RIID_";
			fieldNames[indice++] = "CUSTOMER_ID_";
			fieldNames[indice++] = "EMAIL_ADDRESS_";
			fieldNames[indice++] = "MOBILE_NUMBER_";
			fieldNames[indice++] = "POSTAL_STREET_1_";
			fieldNames[indice++] = "CITY_";
			fieldNames[indice++] = "STATE_";
			fieldNames[indice++] = "POSTAL_CODE_";
			fieldNames[indice++] = "COUNTRY_";
			fieldNames[indice++] = "NOME";
			fieldNames[indice++] = "SOBRENOME";
			fieldNames[indice++] = "SENHA";
			fieldNames[indice++] = "TIPO_DOCUMENTO";
			fieldNames[indice++] = "DOCUMENTO";
			fieldNames[indice++] = "SEXO";
			fieldNames[indice++] = "DATA_NASCIMENTO";
			fieldNames[indice++] = "DATA_REGISTRO";
			fieldNames[indice++] = "NOME_LIDER";

			/**
			 * Dados do records: Lista dos clientes elegíveis à serem criados no CRM.
			 */

			List<String[]> listaRecords = new ArrayList<String[]>();

			Endereco endereco = this._enderecoService.buscaEnderecoPorIdClienteEtipo(cliente.getIdCliente(),
					"residencial");

			if (endereco == null) {

				throw new RuntimeException("Erro: na montagem do layout do cliente id " + cliente.getIdCliente()
						+ ". O endereço residencial não está cadastrado na crm_cad_endereco");
			}

			String[] records = new String[tamanhoVetor];
			indice = 0;

			ClienteAtributo riid = this._clienteAtributoService
					.buscarClienteAtributoPorIdClienteEnomeAtributo(cliente.getIdCliente(), "RIID_");

			if (riid == null) {

				records[indice++] = cliente.getIdCliente().toString();

			} else {

				records[indice++] = riid.getValorAtributo();
			}

			ClienteAtributo codPessoa = this._clienteAtributoService
					.buscarClienteAtributoPorIdClienteEnomeAtributo(cliente.getIdCliente(), "COD_PESSOA");

			if (codPessoa == null) {

				throw new RuntimeException("Erro: na montagem do layout do cliente id " + cliente.getIdCliente()
						+ ". O atributo COD_PESSOA não localizado na tabela crm_cad_atributo_cliente");
			} else {

				records[indice++] = codPessoa.getValorAtributo();
			}

			Contato email = this._contatoService.buscarContatoPorIdClienteEtipo(cliente.getIdCliente(),
					TipoContato.email.getCodigo());

			if (email == null) {

				throw new RuntimeException("Erro: na montagem do layout do cliente id " + cliente.getIdCliente()
						+ ". O tipo 1 não localizado na tabela crm_cad_contato");
			} else {

				records[indice++] = email.getValor();
			}

			Pais celularCodigoPais = this._paisService.buscarPaisPorCodPais(this._contatoService
					.buscarContatoPorIdClienteEtipo(cliente.getIdCliente(), TipoContato.celular.getCodigo())
					.getCodPais());

			Contato celularNumero = this._contatoService.buscarContatoPorIdClienteEtipo(cliente.getIdCliente(),
					TipoContato.celular.getCodigo());

			if (celularCodigoPais == null || celularNumero == null) {

				throw new RuntimeException("Erro: na montagem do layout do cliente id " + cliente.getIdCliente()
						+ ". O codigo do pais e/ou o tipo 2 não localizado na tabela crm_cad_contato");
			} else {

				records[indice++] = "+" + celularCodigoPais.getDdi() + celularNumero.getValor().toString();
			}

			records[indice++] = endereco.getLogradouro() + " " + endereco.getComplemento();
			records[indice++] = endereco.getCidade();
			records[indice++] = endereco.getEstado();
			records[indice++] = endereco.getCep();
			records[indice++] = endereco.getPais();

			records[indice++] = cliente.getNome();
			records[indice++] = cliente.getSobrenome();

			ClienteAtributo senhaPrimeiroAcesso = this._clienteAtributoService
					.buscarClienteAtributoPorIdClienteEnomeAtributo(cliente.getIdCliente(), "SENHA_PRIMEIRO_ACESSO");

			if (senhaPrimeiroAcesso == null) {

				throw new RuntimeException("Erro: na montagem do layout do cliente id " + cliente.getIdCliente()
						+ ". O atributo SENHA_PRIMEIRO_ACESSO não localizado na tabela crm_cad_atributo_cliente");
			} else {

				records[indice++] = senhaPrimeiroAcesso.getValorAtributo();
			}

			records[indice++] = cliente.getTipoDocumento();
			records[indice++] = cliente.getDocumento();
			records[indice++] = cliente.getSexo();
			records[indice++] = cliente.getDataNascimento().toString();
			records[indice++] = cliente.getDataRegistro().toString();

			ClienteAtributo nomeLider = this._clienteAtributoService
					.buscarClienteAtributoPorIdClienteEnomeAtributo(cliente.getIdCliente(), "NOME_LIDER");

			if (nomeLider == null) {

				throw new RuntimeException("Erro: na montagem do layout do cliente id " + cliente.getIdCliente()
						+ ". O atributo NOME_LIDER não localizado na tabela crm_cad_atributo_cliente");
			} else {

				records[indice++] = nomeLider.getValorAtributo();
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
			 * Dados do mergeRule.
			 */

			MergeRule mergeRule = new MergeRule();
			mergeRule.setHtmlValue("H");
			mergeRule.setOptinValue("I");
			mergeRule.setTextValue("T");
			mergeRule.setInsertOnNoMatch(true);
			mergeRule.setUpdateOnMatch("REPLACE_ALL");
			mergeRule.setMatchColumnName1("RIID_");
			mergeRule.setMatchColumnName2("EMAIL_ADDRESS_");
			mergeRule.setMatchOperator("NONE");
			mergeRule.setOptoutValue("O");
			mergeRule.setRejectRecordIfChannelEmpty("");
			mergeRule.setDefaultPermissionStatus("OPTIN");

			/**
			 * Dados do mergeListRecipients: Classe principal, alimentada com os atributos
			 * acima.
			 */

			MergeListRecipients mergeListRecipients = new MergeListRecipients();
			mergeListRecipients.setRecordData(recordData);
			mergeListRecipients.setMergeRule(mergeRule);

			retorno = mergeListRecipients;

		} catch (Exception e) {
			logger.error(
					"ERRO: método " + Thread.currentThread().getStackTrace()[1].getMethodName() + " da classe "
							+ Thread.currentThread().getStackTrace()[1].getClassName() + ": " + e.toString(),
					dateFormat.format(new Date()));
		}

		return retorno;
	}
}
