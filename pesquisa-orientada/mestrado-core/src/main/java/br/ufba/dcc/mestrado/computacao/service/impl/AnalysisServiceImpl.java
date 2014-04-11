package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.AnalysisRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.AnalysisRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.AnalysisService;

@Service(AnalysisServiceImpl.BEAN_NAME)
public class AnalysisServiceImpl extends BaseServiceImpl<Long, OhLohAnalysisEntity>
		implements AnalysisService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3582447874034562222L;

	public static final String BEAN_NAME =  "analysisService";
	
	protected static Logger logger = Logger.getLogger(AnalysisServiceImpl.class.getName());
	
	@Autowired
	public AnalysisServiceImpl(@Qualifier(AnalysisRepositoryImpl.BEAN_NAME) AnalysisRepository repository) {
		super(repository,  OhLohAnalysisEntity.class);
	}

}
