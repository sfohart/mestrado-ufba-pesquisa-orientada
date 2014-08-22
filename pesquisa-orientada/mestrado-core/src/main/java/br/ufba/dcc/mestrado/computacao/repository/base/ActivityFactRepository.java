package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.activityfact.OhLohActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;

public interface ActivityFactRepository extends BaseRepository<Long, OhLohActivityFactEntity>{

	/**
	 * 
	 * @param project
	 * @return
	 */
	Long countAllByProject(OhLohProjectEntity project);
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	List<OhLohActivityFactEntity> findByProject(OhLohProjectEntity project);
	
}
