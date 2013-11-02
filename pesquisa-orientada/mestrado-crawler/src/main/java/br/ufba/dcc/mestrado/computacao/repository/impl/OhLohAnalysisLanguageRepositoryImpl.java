package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisLanguageEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAnalysisLanguageRepository;

@Repository
public class OhLohAnalysisLanguageRepositoryImpl extends BaseRepositoryImpl<Long, OhLohAnalysisLanguageEntity>
		implements OhLohAnalysisLanguageRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohAnalysisLanguageRepositoryImpl() {
		super(OhLohAnalysisLanguageEntity.class);
	}

	
}
