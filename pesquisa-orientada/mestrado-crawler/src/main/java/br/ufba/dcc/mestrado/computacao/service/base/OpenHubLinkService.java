package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubLinkDTO;

public interface OpenHubLinkService extends DefaultOpenHubService<OpenHubLinkDTO, Long, OpenHubLinkEntity>{

	
	List<OpenHubLinkEntity> findByProject(OpenHubProjectEntity project);
	
	Long countAllByProject(OpenHubProjectEntity project);
	
}
