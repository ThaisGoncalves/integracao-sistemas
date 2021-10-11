package br.com.jequiti.crm.responsys.model.sql;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "crm_gop_pagamento")
public class Pagamento {

	@Id
	@Column(name = "id_pagamento")
	private Long idPagamento;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "data_vencimento")
	private Date dataVencimento;

	@Column(name = "data_pagamento")
	private Date dataPagamento;

	@Column(name = "valor_parcela", columnDefinition = "money")
	private Float valorParcela;

	@Column(name = "linha_digitavel")
	private String linhaDigitavel;

	@Column(name = "url_externa")
	private String urlExterna;

	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
}
