package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface UserRecommendationService extends BaseService<Long, UserRecommendationEntity>{
	
	UserRecommendationEntity findLastUserRecommendation(UserEntity user);
	
}
