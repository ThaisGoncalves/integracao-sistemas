package br.com.jequiti.crm.responsys.model.crm;

import lombok.Data;

@Data
public class MergeRule {

	private String htmlValue;
	private String optinValue;
	private String textValue;
	private Boolean insertOnNoMatch;
	private String updateOnMatch;
	private String matchColumnName1;
	private String matchColumnName2;
	private String matchOperator;
	private String optoutValue;
	private String rejectRecordIfChannelEmpty;
	private String defaultPermissionStatus;
}
