package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohLicenseEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohLicenseDTO;

public interface OhLohLicenseService extends DefaultOhLohService<OhLohLicenseDTO, Long, OhLohLicenseEntity>{

	public Long countAll();
	
	public OhLohLicenseEntity findById(Long id);
	
	public List<OhLohLicenseEntity> findAll(Integer startAt, Integer offset);
	
}
