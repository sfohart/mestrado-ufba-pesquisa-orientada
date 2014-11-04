package br.ufba.dcc.mestrado.computacao.service.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.impl.BaseServiceImpl;

@Service(RecommenderCriteriumServiceImpl.BEAN_NAME)
public class RecommenderCriteriumServiceImpl 
		extends BaseServiceImpl<Long, RecommenderCriteriumEntity>
		implements RecommenderCriteriumService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 862915133202416847L;
	
	public static final String BEAN_NAME =  "recommenderCriteriumService";
	
	@Autowired
	public RecommenderCriteriumServiceImpl(@Qualifier("recommenderCriteriumRepository") RecommenderCriteriumRepository repository) {
		super(repository, RecommenderCriteriumEntity.class);
	}

}
