package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;
import java.util.Map;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;

public interface OhLohProjectService extends DefaultOhLohService<OhLohProjectDTO, Long, OhLohProjectEntity>{

	Long countAll();
	
	OhLohProjectEntity findById(Long id);
	
	List<OhLohProjectEntity> findAll(Integer startAt, Integer offset);

	void reloadTagsFromDatabase(OhLohProjectEntity entity) throws Exception;
	void reloadLicensesFromDatabase(OhLohProjectEntity entity) throws Exception;
	void reloadAnalysisFromDatabase(OhLohProjectEntity entity) throws Exception;
	public void reloadProjectActivityIndexFromDatabase(OhLohProjectEntity entity) throws Exception;
	
	Map<String, List<OhLohLinkEntity>> buildLinkMapByCategory(OhLohProjectEntity project);
}
