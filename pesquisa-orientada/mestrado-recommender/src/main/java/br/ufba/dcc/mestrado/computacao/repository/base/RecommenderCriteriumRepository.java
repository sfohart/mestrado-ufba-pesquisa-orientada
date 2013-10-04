package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;

public interface RecommenderCriteriumRepository extends BaseRepository<Long, RecommenderCriteriumEntity>{

	List<RecommenderCriteriumEntity> findAllByAccount(Long accountId); 
	
}
