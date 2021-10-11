package br.com.jequiti.crm.responsys.enums;

import lombok.Getter;

@Getter
public enum TipoEvento {

	criar("INSERT"), 
	consultar("SELECT"), 
	atualizar("UPDATE"), 
	deletar("DELETE");

	private String referencia;

	TipoEvento(String referencia) {
		this.referencia = referencia;
	}
}
