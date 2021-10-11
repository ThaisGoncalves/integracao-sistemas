package br.com.jequiti.crm.responsys.model.sql;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "crm_cad_contato")
public class Contato {

	@Id
	@Column(name = "id_contato")
	private Long idContato;

	@Column(name = "id_cliente")
	private Long idCliente;

	@Column(name = "tipo")
	private int tipo;

	@Column(name = "valor")
	private String valor;

	@Column(name = "recado")
	private String recado;

	@Column(name = "optin")
	private int optin;

	@Column(name = "optout")
	private int optout;

	@Column(name = "cod_pais")
	private String codPais;

	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
}
