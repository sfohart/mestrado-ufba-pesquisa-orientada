package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.sizefact.OpenHubSizeFactEntity;

public interface SizeFactRepository extends BaseRepository<Long, OpenHubSizeFactEntity>{

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
	List<OpenHubSizeFactEntity> findByProject(OpenHubProjectEntity project);
	
}
