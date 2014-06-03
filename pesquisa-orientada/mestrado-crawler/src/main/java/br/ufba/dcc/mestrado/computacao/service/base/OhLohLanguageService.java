package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.language.OhLohLanguageEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageDTO;

public interface OhLohLanguageService extends DefaultOhLohService<OhLohLanguageDTO, Long, OhLohLanguageEntity>{

	public Long countAll();
	
	public OhLohLanguageEntity findById(Long id);
	
	public List<OhLohLanguageEntity> findAll(Integer startAt, Integer offset);
	
}
