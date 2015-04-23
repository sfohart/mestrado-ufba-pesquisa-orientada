package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.UserRecommendationRepository;

@Repository(UserRecommendationRepositoryImpl.BEAN_NAME)
public class UserRecommendationRepositoryImpl 
		extends BaseRepositoryImpl<Long, UserRecommendationEntity>
		implements UserRecommendationRepository {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5717605329155211879L;
	
	public static final String BEAN_NAME =  "userRecommendationRepository";
	
	public UserRecommendationRepositoryImpl() {
		super(UserRecommendationEntity.class);
	}

}
