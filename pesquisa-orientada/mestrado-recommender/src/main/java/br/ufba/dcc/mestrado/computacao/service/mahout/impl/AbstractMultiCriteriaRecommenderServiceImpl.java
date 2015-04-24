package br.ufba.dcc.mestrado.computacao.service.mahout.impl;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.base.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.service.core.impl.AbstractRecommenderServiceImpl;
import br.ufba.dcc.mestrado.computacao.service.mahout.base.MahoutColaborativeFilteringService;
import br.ufba.dcc.mestrado.computacao.service.mahout.base.MultiCriteriaRecommenderService;

@Service
public abstract class AbstractMultiCriteriaRecommenderServiceImpl
		extends AbstractRecommenderServiceImpl
		implements MultiCriteriaRecommenderService {
	
	
	protected MultiCriteriaRecommender multiCriteriaRecommender;
	
	@Autowired
	private MahoutColaborativeFilteringService colaborativeFilteringService;

	
	public AbstractMultiCriteriaRecommenderServiceImpl() {
	}
	
	private void initMultiCriteriaRecommender() {
		if (multiCriteriaRecommender == null) {
			RecommenderBuilder recommenderBuilder = colaborativeFilteringService.getUserBasedRecommenderBuilder();
			this.multiCriteriaRecommender = buildMultiCriteriaRecommender(recommenderBuilder);
		}
	}

	public abstract MultiCriteriaRecommender buildMultiCriteriaRecommender(RecommenderBuilder recommenderBuilder);

	@Override
	public List<RecommendedItem> recommendRatingProjectsByUser(Long userID, Integer howMany) throws TasteException {
		
		initMultiCriteriaRecommender();
		
		List<RecommendedItem> recommendedItems = null;
		try {
			recommendedItems = multiCriteriaRecommender.recommend(userID, howMany);
		} catch (TasteException e) {
			e.printStackTrace();
		}
		
		return recommendedItems;
	}

	@Override
	public List<RecommendedItem> recommendRatingProjectsByUser(
			Long userId,
			Integer howManyItems, 
			boolean filterInterestTags) throws TasteException {
		
		initMultiCriteriaRecommender();
		
		List<RecommendedItem> recommendedItems = null;
		
		if (filterInterestTags) {
			IDRescorer rescorer = buildIdRescorer(userId);				
			recommendedItems = multiCriteriaRecommender.recommend(userId, howManyItems, rescorer, false);
		} else {
			recommendedItems = multiCriteriaRecommender.recommend(userId, howManyItems, false);
		}
		
		return recommendedItems;
	}

	
}
