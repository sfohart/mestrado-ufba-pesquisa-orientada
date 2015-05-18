package br.ufba.dcc.mestrado.computacao.service.recommender.base;

import java.io.Serializable;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public interface CriteriumBasedRecommenderService extends Serializable {


	
	/**
	 * 
	 * @param userId
	 * @param criteriumId
	 * @param howManyItems
	 * @return
	 */
	List<RecommendedItem> recommendRatingProjectsByUserAndCriterium(Long userId, Long criteriumId, Integer howManyItems) throws TasteException;
	
	
	/**
	 * 
	 * @param userId
	 * @param criteriumId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<RecommendedItem> recommendRatingProjectsByUserAndCriterium(Long userId, Long criteriumId, Integer howManyItems, boolean filterInterestTags) throws TasteException;
	
	
	
}
