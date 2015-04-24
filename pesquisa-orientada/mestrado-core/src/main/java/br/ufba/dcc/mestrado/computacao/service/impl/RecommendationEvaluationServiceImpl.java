package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.recommender.evaluation.RecommendationEvaluationEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommendationEvaluationRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.RecommendationEvaluationRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.RecommendationEvaluationService;


@Service(RecommendationEvaluationServiceImpl.BEAN_NAME)
public class RecommendationEvaluationServiceImpl 
		extends BaseServiceImpl<Long, RecommendationEvaluationEntity>
		implements RecommendationEvaluationService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7502996270895144520L;
	
	public static final String BEAN_NAME =  "recommendationEvaluationService";
	
	
	@Autowired
	public RecommendationEvaluationServiceImpl(@Qualifier(RecommendationEvaluationRepositoryImpl.BEAN_NAME) RecommendationEvaluationRepository repository) {
		super(repository, RecommendationEvaluationEntity.class);
	}

}
