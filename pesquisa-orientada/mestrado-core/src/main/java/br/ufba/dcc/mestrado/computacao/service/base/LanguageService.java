package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.language.OhLohLanguageEntity;

public interface LanguageService extends BaseService<Long, OhLohLanguageEntity>{

	public Long countAll();
	
	public OhLohLanguageEntity findById(Long id);
	
	public List<OhLohLanguageEntity> findAll(Integer startAt, Integer offset);
	
}
