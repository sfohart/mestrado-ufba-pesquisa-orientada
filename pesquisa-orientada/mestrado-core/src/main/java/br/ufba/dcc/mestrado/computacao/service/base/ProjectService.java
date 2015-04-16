package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;
import java.util.Map;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;

public interface ProjectService extends BaseService<Long, OpenHubProjectEntity>{

	Long countAll();
	
	OpenHubProjectEntity findById(Long id);
	
	List<OpenHubProjectEntity> findAll(Integer startAt, Integer offset);

	Map<String, List<OpenHubLinkEntity>> buildLinkMapByCategory(OpenHubProjectEntity project);
}
