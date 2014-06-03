package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderCriteriumRepository;

@Repository(RecommenderCriteriumRepositoryImpl.BEAN_NAME)
public class RecommenderCriteriumRepositoryImpl extends BaseRepositoryImpl<Long, RecommenderCriteriumEntity>
	implements RecommenderCriteriumRepository {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7582764504151627470L;
	
	public static final String BEAN_NAME =  "recommenderCriteriumRepository";

	public RecommenderCriteriumRepositoryImpl() {
		super(RecommenderCriteriumEntity.class);
	}
	
}
