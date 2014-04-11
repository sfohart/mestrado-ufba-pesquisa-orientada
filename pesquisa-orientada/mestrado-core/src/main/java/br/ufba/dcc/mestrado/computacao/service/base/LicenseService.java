package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLicenseEntity;

public interface LicenseService extends BaseService<Long, OhLohLicenseEntity>{

	public Long countAll();
	
	public OhLohLicenseEntity findById(Long id);
	
	public List<OhLohLicenseEntity> findAll(Integer startAt, Integer offset);
	
}
