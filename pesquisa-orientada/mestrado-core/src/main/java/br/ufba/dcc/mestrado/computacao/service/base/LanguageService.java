package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.language.OpenHubLanguageEntity;

public interface LanguageService extends BaseService<Long, OpenHubLanguageEntity>{

	public Long countAll();
	
	public OpenHubLanguageEntity findById(Long id);
	
	public List<OpenHubLanguageEntity> findAll(Integer startAt, Integer offset);
	
}
