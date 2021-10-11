package br.com.jequiti.crm.responsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jequiti.crm.responsys.model.sql.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {

	public Pais findByCodPais(String codPais);
}
