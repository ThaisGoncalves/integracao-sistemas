package br.com.jequiti.crm.responsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jequiti.crm.responsys.model.sql.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

	public Contato findByIdClienteAndTipo(Long idCliente, int tipo);
}
