package br.ufba.dcc.mestrado.computacao.recommender.evaluator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.recommender.evaluator.base.OfflineRecommenderEvaluator;
import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.base.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.MahoutColaborativeFilteringService;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.MahoutDataModelService;

@Component
public abstract class AbstractMultiCriteriaRecommenderEvaluator implements OfflineRecommenderEvaluator {

	@Autowired
	private MahoutDataModelService dataModelService;
	
	@Autowired
	private RecommenderCriteriumService criteriumService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectDetailPageViewService pageViewService;
	
	@Autowired
	private RatingByCriteriumService ratingByCriteriumService;
	
	@Value("${recommender.results.sample.max}")
	private int howManyItems;
	
	@Autowired
	private MahoutColaborativeFilteringService colaborativeFilteringService;
	
	@Autowired
	private OverallRatingService overallRatingService;
	
	protected MultiCriteriaRecommender multiCriteriaRecommender;
	
	
	protected Map<RecommenderCriteriumEntity, DataModel> buildDataModelMap() {
		List<RecommenderCriteriumEntity> criteria = criteriumService.findAll();
		Map<RecommenderCriteriumEntity, DataModel> dataModelMap = new HashMap<RecommenderCriteriumEntity, DataModel>();
		
		if (criteria != null) {
			
			for (RecommenderCriteriumEntity criterium : criteria) {
				Map<ImmutablePair<Long,Long>,Double> ratingsMap = ratingByCriteriumService.findAllLastPreferenceByCriterium(criterium.getId());
				
				List<Preference> preferenceList = new ArrayList<Preference>();
				for (Map.Entry<ImmutablePair<Long,Long>,Double> entry: ratingsMap.entrySet()) {
					
					Preference preference = new GenericPreference(
							entry.getKey().getLeft(),
							entry.getKey().getRight(),
							entry.getValue().floatValue()
							);
					
					preferenceList.add(preference);
				}
				
				
				DataModel dataModel = dataModelService.buildDataModelByUser(preferenceList);
				dataModelMap.put(criterium, dataModel);
			}
		}
		
		return dataModelMap;
	}
	
	protected abstract MultiCriteriaRecommender buildMultiCriteriaRecommender(RecommenderBuilder recommenderBuilder);
	
	@Override
	public void evaluate() throws TasteException {
		
		RecommenderBuilder recommenderBuilder = colaborativeFilteringService.getUserBasedRecommenderBuilder();
		
		MultiCriteriaRecommender multiCriteriaRecommender = buildMultiCriteriaRecommender(recommenderBuilder);
		
		List<UserEntity> users = userService.findAll();
		if (users != null) {
			for (UserEntity user : users) {
				
				int successfulRecommendations = 0;
				int failedRecommendations = 0;
				
				List<OpenHubProjectEntity> ratedProjects = overallRatingService.findAllRatedProjectsByUser(user.getId());
				
				if (ratedProjects != null && ratedProjects.size() >= 2) {
					
					List<Long> ids = new ArrayList<Long>();
					for (OpenHubProjectEntity project : ratedProjects) {
						ids.add(project.getId());
					}
										
					List<RecommendedItem> recommendedItems = multiCriteriaRecommender.recommend(user.getId(), howManyItems);
					
					if (recommendedItems != null) {
						boolean found = false;;
						for (RecommendedItem recommended : recommendedItems) {
							if (ids.contains(Long.valueOf(recommended.getItemID()))) {
								found = true;
								break;
							}
						}
						
						if (found) {
							successfulRecommendations++;
						} else {
							failedRecommendations++;
						}
					}
					
					int total = successfulRecommendations + failedRecommendations;
					
					System.out.println(
							String.format("User: %02d | Projects Viewed: %d \t| Successful Recommendations: %d (%.2f%%) \t| Failed Recommendations: %2d (%.2f%%)", 
									user.getId(),
									ratedProjects.size(),
									successfulRecommendations,
									total > 0 ? successfulRecommendations * 100 / total : 0f,
											failedRecommendations,
											total > 0 ? failedRecommendations * 100 / total : 0f
									));
				}
				
			}
		}
		
	}

}
