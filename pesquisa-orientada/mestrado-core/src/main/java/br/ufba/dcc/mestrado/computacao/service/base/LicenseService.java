package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubLicenseEntity;

public interface LicenseService extends BaseService<Long, OpenHubLicenseEntity>{

	public Long countAll();
	
	public OpenHubLicenseEntity findById(Long id);
	
	public List<OpenHubLicenseEntity> findAll(Integer startAt, Integer offset);
	
}
