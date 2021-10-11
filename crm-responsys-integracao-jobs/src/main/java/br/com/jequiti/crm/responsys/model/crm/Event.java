package br.com.jequiti.crm.responsys.model.crm;

import lombok.Data;

@Data
public class Event {

	private CustomEvent customEvent;
	private RecipientData[] recipientData;
}
