package br.com.jequiti.crm.responsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jequiti.crm.responsys.model.sql.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	public List<Endereco> findByIdCliente(Long id);

	public Endereco findByIdClienteAndTipo(Long idCliente, String tipo);
}
