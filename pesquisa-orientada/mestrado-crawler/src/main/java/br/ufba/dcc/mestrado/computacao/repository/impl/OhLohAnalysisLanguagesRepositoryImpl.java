package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisLanguagesEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAnalysisLanguagesRepository;

@Repository
public class OhLohAnalysisLanguagesRepositoryImpl extends BaseRepositoryImpl<Long, OhLohAnalysisLanguagesEntity>
		implements OhLohAnalysisLanguagesRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohAnalysisLanguagesRepositoryImpl() {
		super(OhLohAnalysisLanguagesEntity.class);
	}

	
}
