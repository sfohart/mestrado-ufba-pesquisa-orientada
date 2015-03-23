package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.language.OpenHubLanguageEntity;

public interface LanguageRepository extends BaseRepository<Long, OpenHubLanguageEntity>{

	public OpenHubLanguageEntity findByName(String name);
	
}
