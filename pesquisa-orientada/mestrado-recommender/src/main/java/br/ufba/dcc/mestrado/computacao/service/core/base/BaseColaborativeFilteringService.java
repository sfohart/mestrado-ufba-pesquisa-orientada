
package br.ufba.dcc.mestrado.computacao.service.core.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;

public interface BaseColaborativeFilteringService extends Serializable {
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<OpenHubProjectEntity> recommendViewedProjectsByUser(Long userId, Integer howManyItems);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<OpenHubProjectEntity> recommendViewedProjectsByItem(Long itemId, Integer howManyItems);
	
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<OpenHubProjectEntity> recommendViewedProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<OpenHubProjectEntity> recommendRatingProjectsByUser(Long userId, Integer howManyItems);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<OpenHubProjectEntity> recommendRatingProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags);
	
	/**
	 * 
	 * @param userId
	 * @param criteriumId
	 * @param howManyItems
	 * @return
	 */
	List<OpenHubProjectEntity> recommendRatingProjectsByUserAndCriterium(Long userId, Long criteriumId, Integer howManyItems);
	
	
	/**
	 * 
	 * @param userId
	 * @param criteriumId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<OpenHubProjectEntity> recommendRatingProjectsByUserAndCriterium(Long userId, Long criteriumId, Integer howManyItems, boolean filterInterestTags);
	
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<OpenHubProjectEntity> recommendRandomProjectsByUser(Long userId, Integer howManyItems);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<OpenHubProjectEntity> recommendRandomProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags);

}

