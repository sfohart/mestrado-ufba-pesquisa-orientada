
package br.ufba.dcc.mestrado.computacao.service.core.base;

import java.io.Serializable;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;

public interface BaseColaborativeFilteringService extends Serializable {
	
	/**
	 * 
	 * @param recommendedItems
	 * @return
	 */
	List<OpenHubProjectEntity> getRecommendedProjects(List<RecommendedItem> recommendedItems);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<RecommendedItem> recommendViewedProjectsByUser(Long userId, Integer howManyItems);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<RecommendedItem> recommendViewedProjectsByItem(Long itemId, Integer howManyItems);
	
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<RecommendedItem> recommendViewedProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<RecommendedItem> recommendRatingProjectsByUser(Long userId, Integer howManyItems);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<RecommendedItem> recommendRatingProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags);
	
	/**
	 * 
	 * @param userId
	 * @param criteriumId
	 * @param howManyItems
	 * @return
	 */
	List<RecommendedItem> recommendRatingProjectsByUserAndCriterium(Long userId, Long criteriumId, Integer howManyItems);
	
	
	/**
	 * 
	 * @param userId
	 * @param criteriumId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<RecommendedItem> recommendRatingProjectsByUserAndCriterium(Long userId, Long criteriumId, Integer howManyItems, boolean filterInterestTags);
	
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<RecommendedItem> recommendRandomProjectsByUser(Long userId, Integer howManyItems);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<RecommendedItem> recommendRandomProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags);

}

