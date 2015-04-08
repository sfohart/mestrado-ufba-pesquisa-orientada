package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;
import java.util.Map;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectDTO;

public interface OpenHubProjectService extends DefaultOpenHubService<OpenHubProjectDTO, Long, OpenHubProjectEntity>{

	Long countAll();
	
	OpenHubProjectEntity findById(Long id);
	
	List<OpenHubProjectEntity> findAll(Integer startAt, Integer offset);

	void reloadTagsFromDatabase(OpenHubProjectEntity entity) throws Exception;
	void reloadLicensesFromDatabase(OpenHubProjectEntity entity) throws Exception;
	void reloadAnalysisFromDatabase(OpenHubProjectEntity entity) throws Exception;
	public void reloadProjectActivityIndexFromDatabase(OpenHubProjectEntity entity) throws Exception;
	
	Map<String, List<OpenHubLinkEntity>> buildLinkMapByCategory(OpenHubProjectEntity project);
}
