package br.com.jequiti.crm.responsys.model.sql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "crm_cad_atributo_cliente")
public class ClienteAtributo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_atributo")
	private Long idAtributo;

	@Column(name = "id_cliente")
	private Long idCliente;

	@Column(name = "nome_atributo")
	private String nomeAtributo;

	@Column(name = "valor_atributo")
	private String valorAtributo;
}
