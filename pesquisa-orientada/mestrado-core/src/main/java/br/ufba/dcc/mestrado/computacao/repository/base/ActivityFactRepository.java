package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.activityfact.OpenHubActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;

public interface ActivityFactRepository extends BaseRepository<Long, OpenHubActivityFactEntity>{

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
