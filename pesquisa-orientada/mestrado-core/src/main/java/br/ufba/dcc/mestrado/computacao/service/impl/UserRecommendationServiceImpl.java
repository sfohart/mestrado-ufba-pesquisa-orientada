package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.RecommendationTypeEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.UserRecommendationRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.UserRecommendationRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.UserRecommendationService;

@Service(UserRecommendationServiceImpl.BEAN_NAME)
public class UserRecommendationServiceImpl 
		extends BaseServiceImpl<Long, UserRecommendationEntity>
		implements UserRecommendationService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7365218735871631077L;
	
	public static final String BEAN_NAME =  "userRecommendationService";
	
	@Autowired
	public UserRecommendationServiceImpl(@Qualifier(UserRecommendationRepositoryImpl.BEAN_NAME) UserRecommendationRepository repository) {
		super(repository, UserRecommendationEntity.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserRecommendationEntity findLastUserRecommendation(UserEntity user, RecommendationTypeEnum recommendationType) {
		return ((UserRecommendationRepository) getRepository()).findLastUserRecommendation(user, recommendationType);
	}
	@Override
	@Transactional(readOnly = true)
	public List<UserRecommendationEntity> findAllUserRecommendation(UserEntity user, RecommendationTypeEnum recommendationType) {
		return ((UserRecommendationRepository) getRepository()).findAllUserRecommendation(user, recommendationType);
	}
}
