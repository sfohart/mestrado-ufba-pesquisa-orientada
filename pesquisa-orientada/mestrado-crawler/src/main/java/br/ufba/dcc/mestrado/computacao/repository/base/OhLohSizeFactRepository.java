package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.sizefact.OhLohSizeFactEntity;

public interface OhLohSizeFactRepository extends BaseRepository<Long, OhLohSizeFactEntity>{

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
	List<OhLohSizeFactEntity> findByProject(OhLohProjectEntity project);
	
}
