package br.ufba.dcc.mestrado.computacao.batch.item;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.RecommendationTypeEnum;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.ColaborativeFilteringService;
import br.ufba.dcc.mestrado.computacao.service.recommender.impl.MahoutColaborativeFilteringServiceImpl;

@Component
public class ColaborativeRecommendingProcessor extends AbstractRecommendingProcessor {

	@Autowired
	public ColaborativeRecommendingProcessor(
			@Qualifier(MahoutColaborativeFilteringServiceImpl.BEAN_NAME) ColaborativeFilteringService colaborativeFilteringService) {
		super(colaborativeFilteringService, RecommendationTypeEnum.COLABORATIVE_FILTERING_USER_BASED_RECOMMENDATION);
	}

	@Override
	protected List<RecommendedItem> recommendProjectsByUser(Long userId,
			Integer howManyItems, boolean filterInterestTags) throws TasteException {
		return ((ColaborativeFilteringService) recommenderService).recommendViewedProjectsByUser(userId, howManyItems, filterInterestTags);
	}

}
