package br.com.jequiti.crm.responsys.model.sql;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "crm_cad_cliente")
public class Cliente {

	@Id
	@Column(name = "id_cliente")
	private Long idCliente;

	@Column(name = "nome")
	private String nome;

	@Column(name = "sobrenome")
	private String sobrenome;

	@Column(name = "sexo")
	private String sexo;

	@Column(name = "tipo_documento")
	private String tipoDocumento;

	@Column(name = "documento")
	private String documento;

	@Column(name = "data_registro")
	private Date dataRegistro;

	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;

	@Column(name = "status_cliente")
	private String statusCliente;
}
