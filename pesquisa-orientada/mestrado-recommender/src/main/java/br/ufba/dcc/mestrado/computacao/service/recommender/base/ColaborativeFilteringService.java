
package br.ufba.dcc.mestrado.computacao.service.recommender.base;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public interface ColaborativeFilteringService extends BaseRecommenderService , CriteriumBasedRecommenderService {

	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<RecommendedItem> recommendViewedProjectsByUser(Long userId, Integer howManyItems) throws TasteException;
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<RecommendedItem> recommendViewedProjectsByItem(Long itemId, Integer howManyItems) throws TasteException;
	
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<RecommendedItem> recommendViewedProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags) throws TasteException;
	

	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<RecommendedItem> recommendRandomProjectsByUser(Long userId, Integer howManyItems) throws TasteException;
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<RecommendedItem> recommendRandomProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags) throws TasteException;

}

