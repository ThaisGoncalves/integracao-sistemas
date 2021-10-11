package br.com.jequiti.crm.responsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jequiti.crm.responsys.model.sql.ClienteAtributo;

@Repository
public interface ClienteAtributoRepository extends JpaRepository<ClienteAtributo, Long> {

	public ClienteAtributo findByIdClienteAndNomeAtributo(Long idCliente, String nomeAtributo);
}
