package br.ufba.dcc.mestrado.computacao.service.mahout.base;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.base.MultiCriteriaRecommender;

public interface MultiCriteriaRecommenderService {

	
	MultiCriteriaRecommender buildMultiCriteriaRecommender(RecommenderBuilder recommenderBuilder);
	
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
	 * @param recommendedItems
	 * @return
	 */
	List<OpenHubProjectEntity> getRecommendedProjects(List<RecommendedItem> recommendedItems);
	
	
}
