package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.analysis.OhLohAnalysisEntity;

public interface AnalysisService extends BaseService<Long, OhLohAnalysisEntity>{

	public Long countAll();
	
	public OhLohAnalysisEntity findById(Long id);
	
	public List<OhLohAnalysisEntity> findAll(Integer startAt, Integer offset);
	
}
