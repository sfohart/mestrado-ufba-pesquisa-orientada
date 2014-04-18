package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.UserRecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.BaseRepository;

public interface UserRecommenderCriteriumRepository extends BaseRepository<Long, UserRecommenderCriteriumEntity>{

	List<UserRecommenderCriteriumEntity> findAllByUser(Long userId);
	
}
