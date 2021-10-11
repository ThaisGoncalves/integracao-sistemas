package br.com.jequiti.crm.responsys.model.sql;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "crm_gop_pedido")
public class Pedido {

	@Id
	@Column(name = "id_pedido")
	private Long idPedido;

	@Column(name = "id_cliente")
	private Long idCliente;

	@Column(name = "cod_pedido")
	private Long codPedido;

	@Column(name = "cod_pedido_referencia")
	private String codPedidoReferencia;

	@Column(name = "obs")
	private String obs;

	@Column(name = "data_pedido")
	private Date dataPedido;

	@Column(name = "data_entrega")
	private Date dataEntrega;

	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;

	@Column(name = "status_pedido")
	private String statusPedido;
}
