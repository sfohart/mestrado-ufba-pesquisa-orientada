package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisLanguagesEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAnalysisLanguagesRepository;

@Repository(OhLohAnalysisLanguagesRepositoryImpl.BEAN_NAME)
public class OhLohAnalysisLanguagesRepositoryImpl extends BaseRepositoryImpl<Long, OhLohAnalysisLanguagesEntity>
		implements OhLohAnalysisLanguagesRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohAnalysisLanguagesRepository";

	public OhLohAnalysisLanguagesRepositoryImpl() {
		super(OhLohAnalysisLanguagesEntity.class);
	}

	
}
