package br.ufba.dcc.mestrado.computacao.batch.item;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.RecommendationTypeEnum;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.MultiCriteriaRecommenderService;
import br.ufba.dcc.mestrado.computacao.service.recommender.impl.MultiCriteriaListBasedRecommenderServiceImpl;

@Component
public class MultiCriteriaRecommendingProcessor extends AbstractRecommendingProcessor {

	
	@Autowired
	public MultiCriteriaRecommendingProcessor(
			@Qualifier(MultiCriteriaListBasedRecommenderServiceImpl.BEAN_NAME) MultiCriteriaRecommenderService multiCriteriaRecommenderService) {
		super(multiCriteriaRecommenderService, RecommendationTypeEnum.MULTICRITERIA_LIST_BASED_RECOMMENDATION);
	}

	@Override
	protected List<RecommendedItem> recommendProjectsByUser(Long userId,
			Integer howManyItems, 
			boolean filterInterestTags)
			throws TasteException {
		return ((MultiCriteriaRecommenderService) recommenderService).recommendRatingProjectsByUser(userId, howManyItems, filterInterestTags);
	}
	
}
