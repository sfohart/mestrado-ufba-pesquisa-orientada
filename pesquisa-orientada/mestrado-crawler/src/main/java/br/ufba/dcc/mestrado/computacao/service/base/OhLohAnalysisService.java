package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.analysis.OhLohAnalysisDTO;

public interface OhLohAnalysisService extends DefaultOhLohService<OhLohAnalysisDTO, Long, OhLohAnalysisEntity>{

	public Long countAll();
	
	public OhLohAnalysisEntity findById(Long id);
	
	public List<OhLohAnalysisEntity> findAll(Integer startAt, Integer offset);
	
}
