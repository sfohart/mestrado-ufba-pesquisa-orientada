package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.activityfact.OhLohActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;

public interface OhLohActivityFactRepository extends BaseRepository<Long, OhLohActivityFactEntity>{

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
