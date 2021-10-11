package br.com.jequiti.crm.responsys.model.crm;

import lombok.Data;

@Data
public class Recipient {

	private String emailAddress;
	private ListName listName;
	private String recipientId;
	private String mobileNumber;
	private String emailFormat;
}
