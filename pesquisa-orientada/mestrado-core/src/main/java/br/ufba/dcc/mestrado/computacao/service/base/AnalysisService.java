package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.analysis.OpenHubAnalysisEntity;

public interface AnalysisService extends BaseService<Long, OpenHubAnalysisEntity>{

	public Long countAll();
	
	public OpenHubAnalysisEntity findById(Long id);
	
	public List<OpenHubAnalysisEntity> findAll(Integer startAt, Integer offset);
	
}
