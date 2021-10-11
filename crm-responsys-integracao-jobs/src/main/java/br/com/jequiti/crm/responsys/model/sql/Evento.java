package br.com.jequiti.crm.responsys.model.sql;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "crm_sis_evento")
public class Evento {

	@Id
	@Column(name = "id_evento")
	private Long idEvento;

	@Column(name = "tipo_evento")
	private String tipoEvento;

	@Column(name = "tabela_origem")
	private String tabelaOrigem;

	@Column(name = "chave_tabela")
	private String chaveTabela;

	@Column(name = "data_evento")
	private Date dataEvento;

	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
}
