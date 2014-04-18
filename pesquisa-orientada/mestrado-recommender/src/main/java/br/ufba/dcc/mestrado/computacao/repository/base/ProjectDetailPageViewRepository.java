package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.BaseRepository;

public interface ProjectDetailPageViewRepository extends BaseRepository<Long, ProjectDetailPageViewEntity>{
	
	/**
	 * 
	 * @return 	uma lista de pares do tipo (userId,itemId), 
	 * 			onde userId é o id do usuário, e itemId é o id do projeto que o usuário visualizou  
	 */
	List<ImmutablePair<UserEntity, OhLohProjectEntity>> findAllProjectDetailViews();
	
	List<ImmutablePair<UserEntity, OhLohProjectEntity>> findAllProjectDetailViews(
			Integer startAt, 
			Integer offset);
	
	
	/**
	 * 
	 * @param user
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<OhLohProjectEntity> findAllProjectRecentlyViewed(
			UserEntity user,
			Integer startAt, 
			Integer offset);
	
	/**
	 * 
	 * @return
	 */
	List<ImmutablePair<OhLohProjectEntity, Long>> findAllProjectDetailViewsCount();
	
	/**
	 * 
	 * @return
	 */
	List<ImmutablePair<OhLohProjectEntity, Long>> findAllProjectDetailViewsCount(
			Integer startAt, 
			Integer offset);
	
	
}
