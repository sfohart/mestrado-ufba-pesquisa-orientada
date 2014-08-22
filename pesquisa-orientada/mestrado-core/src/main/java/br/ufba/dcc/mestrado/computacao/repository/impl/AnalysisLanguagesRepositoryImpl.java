package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.analysis.OhLohAnalysisLanguagesEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.AnalysisLanguagesRepository;

@Repository(AnalysisLanguagesRepositoryImpl.BEAN_NAME)
public class AnalysisLanguagesRepositoryImpl extends BaseRepositoryImpl<Long, OhLohAnalysisLanguagesEntity>
		implements AnalysisLanguagesRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "analysisLanguagesRepository";

	public AnalysisLanguagesRepositoryImpl() {
		super(OhLohAnalysisLanguagesEntity.class);
	}

	
}
