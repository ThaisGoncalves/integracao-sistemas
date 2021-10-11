package br.com.jequiti.crm.responsys.model.crm;

import lombok.Data;

@Data
public class Authentication {

	private String authToken;
	private Long issuedAt;
	private String endPoint;
}
