package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.criterium.UserRecommenderCriteriumEntity;

public interface UserRecommenderCriteriumRepository extends BaseRepository<Long, UserRecommenderCriteriumEntity>{

	List<UserRecommenderCriteriumEntity> findAllByUser(Long userId);
	
}
