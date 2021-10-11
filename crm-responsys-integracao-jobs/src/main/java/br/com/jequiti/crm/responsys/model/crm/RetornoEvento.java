package br.com.jequiti.crm.responsys.model.crm;

import lombok.Data;

@Data
public class RetornoEvento {

	private Boolean success;
	private Long recipientId;
	private String errorMessage;
}
