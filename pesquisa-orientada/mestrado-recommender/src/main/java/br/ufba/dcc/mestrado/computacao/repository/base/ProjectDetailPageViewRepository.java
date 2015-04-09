
package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;


public interface ProjectDetailPageViewRepository extends BaseRepository<Long, ProjectDetailPageViewEntity>{
	
	/**
	 * 
	 * @return 	uma lista de pares do tipo (userId,itemId), 
	 * 			onde userId � o id do usu�rio, e itemId � o id do projeto que o usu�rio visualizou  
	 */
	List<ImmutablePair<UserEntity, OpenHubProjectEntity>> findAllProjectDetailViews();
	
	List<ImmutablePair<UserEntity, OpenHubProjectEntity>> findAllProjectDetailViews(
			Integer startAt, 
			Integer offset);
	
	
	/**
	 * 
	 * @param user
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<OpenHubProjectEntity> findAllProjectRecentlyViewed(
			UserEntity user,
			Integer startAt, 
			Integer offset);
	
	/**
	 * 
	 * @return
	 */
	List<ImmutablePair<OpenHubProjectEntity, Long>> findAllProjectDetailViewsCount();
	
	/**
	 * 
	 * @return
	 */
	List<ImmutablePair<OpenHubProjectEntity, Long>> findAllProjectDetailViewsCount(
			Integer startAt, 
			Integer offset);
	
	
}

