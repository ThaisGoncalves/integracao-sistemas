package br.com.jequiti.crm.responsys.service.provider;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.jequiti.crm.responsys.exception.RuntimeException;
import br.com.jequiti.crm.responsys.model.crm.Authentication;
import br.com.jequiti.crm.responsys.service.CrmAuthenticationRestService;

@Service
public class CrmAuthenticationRestServiceProvider implements CrmAuthenticationRestService {

	private static final Logger logger = LoggerFactory.getLogger(CrmAuthenticationRestServiceProvider.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private RestTemplate _restTemplate;

	@Value("${responsys9.api.authentication}")
	private String authenticationUrl;

	@Value("${responsys9.api.username}")
	private String username;

	@Value("${responsys9.api.password}")
	private String password;

	@Value("${responsys.api.grant_type}")
	private String authType;

	@Override
	public Authentication authenticate() {

		Authentication retorno = null;

		try {

			String url = String.format(authenticationUrl, username, password, authType);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<String> params = new HttpEntity<>(headers);

			ResponseEntity<Authentication> response = this._restTemplate.postForEntity(url, params,
					Authentication.class);
			if (response.getStatusCode() == HttpStatus.OK) {

				retorno = response.getBody();

			} else {

				throw new RuntimeException("ERRO: na autenticação do CRM: " + response.getBody());
			}

		} catch (Exception e) {
			logger.error(
					"ERRO: método " + Thread.currentThread().getStackTrace()[1].getMethodName() + " da classe "
							+ Thread.currentThread().getStackTrace()[1].getClassName() + ": " + e.toString(),
					dateFormat.format(new Date()));
		}

		return retorno;
	}
}
