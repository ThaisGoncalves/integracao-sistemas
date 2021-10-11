package br.com.jequiti.crm.responsys.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jequiti.crm.responsys.model.sql.Pais;
import br.com.jequiti.crm.responsys.repository.PaisRepository;
import br.com.jequiti.crm.responsys.service.PaisService;

@Service
public class PaisServiceProvider implements PaisService {

	@Autowired
	private PaisRepository _paisRepository;

	@Override
	public Pais buscarPaisPorCodPais(String codPais) throws Exception {

		return this._paisRepository.findByCodPais(codPais);
	}
}
