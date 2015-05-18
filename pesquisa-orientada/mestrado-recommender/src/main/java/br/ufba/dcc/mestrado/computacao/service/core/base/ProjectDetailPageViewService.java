
package br.ufba.dcc.mestrado.computacao.service.core.base;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.BaseService;

public interface ProjectDetailPageViewService extends BaseService<Long, ProjectDetailPageViewEntity> {

	/**
	 * 
	 * @return 	uma lista de pares do tipo (userId,itemId), 
	 * 			onde userId é o id do usuário, e itemId é o id do projeto que o usuário visualizou  
	 */
	List<ImmutablePair<UserEntity, OpenHubProjectEntity>> findAllProjectDetailViews();
	
	
	/**
	 * 
	 * @return 	uma lista de pares do tipo (userId,itemId), 
	 * 			onde userId é o id do usuário, e itemId é o id do projeto que o usuário visualizou  
	 */
	List<ImmutablePair<UserEntity, OpenHubProjectEntity>> findAllProjectDetailViews(
			Integer startAt, 
			Integer offset);
	
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public List<OpenHubProjectEntity> findAllProjectRecentlyViewedByUser(UserEntity user);
	
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public Long countAllProjectRecentlyViewedByUser(UserEntity user);
	
	/**
	 * 
	 * @param user
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<OpenHubProjectEntity> findAllProjectRecentlyViewedByUser(
			UserEntity user,
			Integer startAt, 
			Integer offset);
	
	/**
	 * 
	 * @return
	 */
	List<ImmutablePair<OpenHubProjectEntity, Long>> groupProjectDetailViewsCount();
	
	/**
	 * 
	 * @return
	 */
	List<ImmutablePair<OpenHubProjectEntity, Long>> groupProjectDetailViewsCount(
			Integer startAt, 
			Integer offset);
	
	
}

