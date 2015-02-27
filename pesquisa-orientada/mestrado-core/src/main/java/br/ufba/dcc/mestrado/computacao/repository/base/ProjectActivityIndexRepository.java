package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectActivityIndexEntity;

public interface ProjectActivityIndexRepository extends BaseRepository<Long, OhLohProjectActivityIndexEntity>{

	public OhLohProjectActivityIndexEntity findByValue(Long value);
	
	
}
