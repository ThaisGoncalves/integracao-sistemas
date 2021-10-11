package br.com.jequiti.crm.responsys.model.crm;

import lombok.Data;

@Data
public class Requisicao {

	private String url;
	private String token;
	private Object objeto;
	private String operacao;
}
