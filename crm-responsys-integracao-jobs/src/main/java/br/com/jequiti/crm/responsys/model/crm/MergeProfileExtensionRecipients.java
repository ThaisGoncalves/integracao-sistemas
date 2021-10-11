package br.com.jequiti.crm.responsys.model.crm;

import lombok.Data;

@Data
public class MergeProfileExtensionRecipients {

	private RecordData recordData;
	private boolean insertOnNoMatch;
	private String updateOnMatch;
	private String matchColumnName1;
	private String matchColumnName2;
}
