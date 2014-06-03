package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.sizefact.OhLohSizeFactEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.sizefact.OhLohSizeFactDTO;

public interface OhLohSizeFactService extends DefaultOhLohService<OhLohSizeFactDTO, Long, OhLohSizeFactEntity>{

	
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
