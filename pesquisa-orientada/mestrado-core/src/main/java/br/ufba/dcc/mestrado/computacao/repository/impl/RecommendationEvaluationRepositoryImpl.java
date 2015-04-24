package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.recommender.evaluation.RecommendationEvaluationEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommendationEvaluationRepository;


@Repository(RecommendationEvaluationRepositoryImpl.BEAN_NAME)
public class RecommendationEvaluationRepositoryImpl 
		extends BaseRepositoryImpl<Long, RecommendationEvaluationEntity>
		implements RecommendationEvaluationRepository {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 588234278333728645L;
	
	public static final String BEAN_NAME =  "recommendationEvaluationRepository";
	
	public RecommendationEvaluationRepositoryImpl() {
		super(RecommendationEvaluationEntity.class);
	}

}
