package br.com.jequiti.crm.responsys.model.sql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "crm_gop_atributo_pedido")
public class PedidoAtributo {

	@Id
	@Column(name = "id_atributo")
	private Long idAtributo;

	@Column(name = "id_pedido")
	private Long idPedido;

	@Column(name = "nome_atributo")
	private String nomeAtributo;

	@Column(name = "valor_atributo")
	private String valorAtributo;
}
