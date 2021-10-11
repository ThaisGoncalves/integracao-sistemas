package br.com.jequiti.crm.responsys.model.sql;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "crm_cad_endereco")
public class Endereco {

	@Id
	@Column(name = "id_endereco")
	private Long idEndereco;

	@Column(name = "id_cliente")
	private Long idCliente;

	@Column(name = "logradouro")
	private String logradouro;

	@Column(name = "cidade")
	private String cidade;

	@Column(name = "cep")
	private String cep;

	@Column(name = "numero")
	private String numero;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "referencia")
	private String referencia;

	@Column(name = "estado")
	private String estado;

	@Column(name = "pais")
	private String pais;

	@Column(name = "igbe")
	private Long igbe;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
}
