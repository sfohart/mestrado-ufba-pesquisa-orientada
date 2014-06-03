package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohLicenseEntity;

public interface LicenseRepository extends BaseRepository<Long, OhLohLicenseEntity>{

	public OhLohLicenseEntity findByName(String name);
	
}
