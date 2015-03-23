package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;

public interface LinkService extends BaseService<Long, OpenHubLinkEntity>{

	
	List<OpenHubLinkEntity> findByProject(OpenHubProjectEntity project);
	
	Long countAllByProject(OpenHubProjectEntity project);
	
}
