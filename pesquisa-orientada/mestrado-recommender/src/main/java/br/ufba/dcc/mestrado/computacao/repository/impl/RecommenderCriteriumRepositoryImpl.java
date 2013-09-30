package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderCriteriumRepository;

@Repository
public class RecommenderCriteriumRepositoryImpl extends BaseRepositoryImpl<Long, RecommenderCriteriumEntity>
	implements RecommenderCriteriumRepository {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7582764504151627470L;

	public RecommenderCriteriumRepositoryImpl() {
		super(RecommenderCriteriumEntity.class);
	}
}
