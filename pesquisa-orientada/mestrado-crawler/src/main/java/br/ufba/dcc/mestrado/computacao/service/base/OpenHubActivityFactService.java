package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.activityfact.OpenHubActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.activityfact.OpenHubActivityFactDTO;

public interface OpenHubActivityFactService extends DefaultOpenHubService<OpenHubActivityFactDTO, Long, OpenHubActivityFactEntity>{

	
	/**
	 * 
	 * @param project
	 * @return
	 */
	Long countAllByProject(OpenHubProjectEntity project);
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	List<OpenHubActivityFactEntity> findByProject(OpenHubProjectEntity project);
	
}
