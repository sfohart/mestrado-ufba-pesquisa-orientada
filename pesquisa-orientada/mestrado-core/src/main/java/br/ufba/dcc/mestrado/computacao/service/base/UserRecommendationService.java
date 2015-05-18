package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.RecommendationTypeEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface UserRecommendationService extends BaseService<Long, UserRecommendationEntity>{
	
	UserRecommendationEntity findLastUserRecommendation(UserEntity user, RecommendationTypeEnum recommendationType);
	
	List<UserRecommendationEntity> findAllUserRecommendation(UserEntity user, RecommendationTypeEnum recommendationType);
	
}
