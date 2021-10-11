package br.com.jequiti.crm.responsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jequiti.crm.responsys.model.sql.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

	@Query(value = "select top 10* from crm_sis_evento where tabela_origem = :origem", nativeQuery = true)
	public List<Evento> findByTabelaOrigem(@Param("origem") String tabelaOrigem);
	
	public void deleteByIdEvento(Long idEvento);
}
