package br.com.jequiti.crm.responsys.model.sql;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "crm_gop_pedido_linha")
public class PedidoLinha {

	@Id
	@Column(name = "id_pedido_linha")
	private Long idPedidoLinha;

	@Column(name = "id_pedido")
	private Long idPedido;

	@Column(name = "codigo_produto")
	private String codigoProduto;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "quantidade")
	private Long quantidade;

	@Column(name = "valor_unitario", columnDefinition = "money")
	private Float valorUnitario;

	@Column(name = "valor_desconto", columnDefinition = "money")
	private Float valorDesconto;

	@Column(name = "valor_total", columnDefinition = "money")
	private Float valorTotal;

	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;

	@Column(name = "status_linha")
	private String statusLinha;
}
