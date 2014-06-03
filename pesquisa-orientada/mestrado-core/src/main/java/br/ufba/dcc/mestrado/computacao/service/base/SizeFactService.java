package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.sizefact.OhLohSizeFactEntity;

public interface SizeFactService extends BaseService<Long, OhLohSizeFactEntity>{

	
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
