package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderCriteriumService;

@Service(RecommenderCriteriumServiceImpl.BEAN_NAME)
public class RecommenderCriteriumServiceImpl 
		extends BasicRecommenderServiceImpl<Long, RecommenderCriteriumEntity>
		implements RecommenderCriteriumService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 862915133202416847L;
	
	public static final String BEAN_NAME =  "recommenderCriteriumService";
	
	@Autowired
	public RecommenderCriteriumServiceImpl(RecommenderCriteriumRepository repository) {
		super(repository, RecommenderCriteriumEntity.class);
	}

}
