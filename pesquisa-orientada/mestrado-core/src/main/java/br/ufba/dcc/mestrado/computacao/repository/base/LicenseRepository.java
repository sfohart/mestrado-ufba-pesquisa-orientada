package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubLicenseEntity;

public interface LicenseRepository extends BaseRepository<Long, OpenHubLicenseEntity>{

	public OpenHubLicenseEntity findByName(String name);
	
}
