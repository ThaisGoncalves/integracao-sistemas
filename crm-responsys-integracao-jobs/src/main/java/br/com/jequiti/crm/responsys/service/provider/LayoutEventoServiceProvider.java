package br.com.jequiti.crm.responsys.service.provider;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.enums.TipoContato;
import br.com.jequiti.crm.responsys.model.crm.CustomEvent;
import br.com.jequiti.crm.responsys.model.crm.Event;
import br.com.jequiti.crm.responsys.model.crm.ListName;
import br.com.jequiti.crm.responsys.model.crm.Recipient;
import br.com.jequiti.crm.responsys.model.crm.RecipientData;
import br.com.jequiti.crm.responsys.model.sql.Cliente;
import br.com.jequiti.crm.responsys.service.ContatoService;
import br.com.jequiti.crm.responsys.service.LayoutEventoService;

@Service
public class LayoutEventoServiceProvider implements LayoutEventoService {

	private static final Logger logger = LoggerFactory.getLogger(LayoutEventoServiceProvider.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private ContatoService _contatoService;

	@Override
	public Event montar(Cliente cliente, ListName listNameParametros) {

		Event retorno = null;

		try {

			/**
			 * Dados do customEvent.
			 */

			CustomEvent customEvent = new CustomEvent();
			customEvent.setEventNumberDataMapping("");
			customEvent.setEventDateDataMapping("");
			customEvent.setEventStringDataMapping("");

			/**
			 * Dados do listName.
			 */

			ListName listName = new ListName();
			listName.setFolderName(listNameParametros.getFolderName());
			listName.setObjectName(listNameParametros.getObjectName());

			/**
			 * Dados do recipient.
			 */

			Recipient recipient = new Recipient();
			recipient.setEmailAddress(this._contatoService
					.buscarContatoPorIdClienteEtipo(cliente.getIdCliente(), TipoContato.email.getCodigo()).getValor());
			recipient.setListName(listName);
			recipient.setRecipientId("");
			recipient.setMobileNumber("");
			recipient.setEmailFormat("HTML_FORMAT");

			/**
			 * Dados do recipientData.
			 */

			RecipientData recipientDataRecipient = new RecipientData();
			recipientDataRecipient.setRecipient(recipient);

			RecipientData[] recipientData = new RecipientData[1];
			recipientData[0] = recipientDataRecipient;

			/**
			 * Dados do Evento: Classe principal, alimentada com os atributos acima.
			 */

			Event ativacaoCadastro = new Event();
			ativacaoCadastro.setCustomEvent(customEvent);
			ativacaoCadastro.setRecipientData(recipientData);

			retorno = ativacaoCadastro;

		} catch (Exception e) {
			logger.error(
					"ERRO: Metodo " + Thread.currentThread().getStackTrace()[1].getMethodName() + " da classe "
							+ Thread.currentThread().getStackTrace()[1].getClassName() + ": " + e.toString(),
					dateFormat.format(new Date()));
		}

		return retorno;
	}
}
