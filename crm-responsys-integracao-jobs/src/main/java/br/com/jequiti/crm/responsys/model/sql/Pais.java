package br.com.jequiti.crm.responsys.model.sql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "crm_cad_pais")
public class Pais {

	@Id
	@Column(name = "id_pais")
	private Long idPais;

	@Column(name = "pais")
	private String pais;

	@Column(name = "cod_iso")
	private String codIso;

	@Column(name = "cod_pais")
	private String codPais;

	@Column(name = "ddi")
	private int ddi;
}
