package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.language.OhLohLanguageEntity;

public interface LanguageRepository extends BaseRepository<Long, OhLohLanguageEntity>{

	public OhLohLanguageEntity findByName(String name);
	
}