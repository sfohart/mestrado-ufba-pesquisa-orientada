package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface UserRecommendationRepository extends BaseRepository<Long, UserRecommendationEntity>{

	
	UserRecommendationEntity findLastUserRecommendation(UserEntity user);
	
}
