package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectActivityIndexEntity;

public interface ProjectActivityIndexRepository extends BaseRepository<Long, OpenHubProjectActivityIndexEntity>{

	public OpenHubProjectActivityIndexEntity findByValue(Long value);
	
	
}
