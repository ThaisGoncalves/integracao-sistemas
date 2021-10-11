package br.com.jequiti.crm.responsys.service.provider;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jequiti.crm.responsys.exception.RuntimeException;
import br.com.jequiti.crm.responsys.model.crm.Requisicao;
import br.com.jequiti.crm.responsys.service.RequisicaoService;

@Service
public class RequisicaoServiceProvider implements RequisicaoService {

	private static final Logger logger = LoggerFactory.getLogger(RequisicaoServiceProvider.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private RestTemplate _restTemplate;

	@Override
	public String requerer(Requisicao requisicao) {

		String retorno = null;

		try {

			HttpHeaders headers = new HttpHeaders();

			if (requisicao.getToken() != null) {
				headers.add("Authorization", requisicao.getToken());
			}

			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = null;
			if (requisicao.getObjeto() != null) {

				String json = new ObjectMapper().writeValueAsString(requisicao.getObjeto());
				entity = new HttpEntity<String>(json, headers);

			} else {
				entity = new HttpEntity<String>(headers);
			}

			ResponseEntity<String> response = null;
			switch (requisicao.getOperacao()) {
			case "UPDATE":
				response = this._restTemplate.exchange(requisicao.getUrl(), HttpMethod.POST, entity, String.class);
				break;
			case "SELECT":
				response = this._restTemplate.exchange(requisicao.getUrl(), HttpMethod.GET, entity, String.class);
				break;
			case "DELETE":
				response = this._restTemplate.exchange(requisicao.getUrl(), HttpMethod.DELETE, entity, String.class);
				break;
			default:
				throw new RuntimeException(
						"ERRO: operação " + requisicao.getOperacao() + " não identificada no sistema");
			}

			retorno = response.getBody();
			if (response.getStatusCode() != HttpStatus.OK) {

				throw new RuntimeException("ERRO: no envio de envio de dados: " + response.getBody());
			}

		} catch (Exception e) {
			logger.error(
					"ERRO: metodo " + Thread.currentThread().getStackTrace()[1].getMethodName() + " da classe "
							+ Thread.currentThread().getStackTrace()[1].getClassName() + ": " + e.toString() + " <<===",
					dateFormat.format(new Date()));
		}

		return retorno;
	}
}
