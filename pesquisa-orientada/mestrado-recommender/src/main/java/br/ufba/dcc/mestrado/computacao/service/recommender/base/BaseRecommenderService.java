package br.ufba.dcc.mestrado.computacao.service.recommender.base;

import java.io.Serializable;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;

public interface BaseRecommenderService extends Serializable {

	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<RecommendedItem> recommendRatingProjectsByUser(Long userId, Integer howManyItems) throws TasteException;
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<RecommendedItem> recommendRatingProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags) throws TasteException;
	
	/**
	 * 
	 * @param recommendedItems
	 * @return
	 */
	List<OpenHubProjectEntity> getRecommendedProjects(List<RecommendedItem> recommendedItems) throws TasteException;
	
}
