package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.recommender.evaluation.RecommenderEvaluationEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderEvaluationRepository;


@Repository(RecommenderEvaluationRepositoryImpl.BEAN_NAME)
public class RecommenderEvaluationRepositoryImpl 
		extends BaseRepositoryImpl<Long, RecommenderEvaluationEntity>
		implements RecommenderEvaluationRepository {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 588234278333728645L;
	
	public static final String BEAN_NAME =  "recommenderEvaluationRepository";
	
	public RecommenderEvaluationRepositoryImpl() {
		super(RecommenderEvaluationEntity.class);
	}

}
