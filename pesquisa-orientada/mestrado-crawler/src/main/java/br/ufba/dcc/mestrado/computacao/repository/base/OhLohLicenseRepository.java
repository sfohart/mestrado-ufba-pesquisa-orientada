package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLicenseEntity;

public interface OhLohLicenseRepository extends BaseRepository<Long, OhLohLicenseEntity>{

	public OhLohLicenseEntity findByName(String name);
	
}
