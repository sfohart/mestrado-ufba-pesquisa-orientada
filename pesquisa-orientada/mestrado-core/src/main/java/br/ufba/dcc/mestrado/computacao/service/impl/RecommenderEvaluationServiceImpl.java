package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.recommender.evaluation.RecommenderEvaluationEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderEvaluationRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.RecommenderEvaluationRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderEvaluationService;


@Service(RecommenderEvaluationServiceImpl.BEAN_NAME)
public class RecommenderEvaluationServiceImpl 
		extends BaseServiceImpl<Long, RecommenderEvaluationEntity>
		implements RecommenderEvaluationService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7502996270895144520L;
	
	public static final String BEAN_NAME =  "recommenderEvaluationService";
	
	
	@Autowired
	public RecommenderEvaluationServiceImpl(@Qualifier(RecommenderEvaluationRepositoryImpl.BEAN_NAME) RecommenderEvaluationRepository repository) {
		super(repository, RecommenderEvaluationEntity.class);
	}

}
