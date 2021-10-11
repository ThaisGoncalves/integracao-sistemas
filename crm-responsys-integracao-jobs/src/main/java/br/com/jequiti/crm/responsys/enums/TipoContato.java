package br.com.jequiti.crm.responsys.enums;

import lombok.Getter;

@Getter
public enum TipoContato {

	email(1), 
	celular(2);

	private int codigo;

	TipoContato(int i) {
		this.codigo = i;
	}
}
