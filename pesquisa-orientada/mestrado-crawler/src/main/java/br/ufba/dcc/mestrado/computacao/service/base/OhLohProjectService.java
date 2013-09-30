package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;

public interface OhLohProjectService extends BaseOhLohService<OhLohProjectDTO, Long, OhLohProjectEntity>{

	public Long countAll();
	
	public OhLohProjectEntity findById(Long id);
	
	public List<OhLohProjectEntity> findAll(Integer startAt, Integer offset);

	public abstract void reloadTagsFromDatabase(OhLohProjectEntity entity) throws Exception;

	public abstract void reloadLicensesFromDatabase(OhLohProjectEntity entity) throws Exception;

	public abstract void reloadAnalysisFromDatabase(OhLohProjectEntity entity) throws Exception;
	
}
