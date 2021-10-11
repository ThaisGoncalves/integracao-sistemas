package br.com.jequiti.crm.responsys.model.crm;

import java.util.List;

import lombok.Data;

@Data
public class RecordData {

	private String[] fieldNames;
	private List<String[]> records;
	private String mapTemplateName;
}
