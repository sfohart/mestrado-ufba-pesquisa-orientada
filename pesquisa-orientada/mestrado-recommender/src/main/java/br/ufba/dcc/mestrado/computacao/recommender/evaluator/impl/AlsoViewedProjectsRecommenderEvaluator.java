package br.ufba.dcc.mestrado.computacao.recommender.evaluator.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.recommender.evaluator.base.OfflineRecommenderEvaluator;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.mahout.base.MahoutColaborativeFilteringService;

@Component
public class AlsoViewedProjectsRecommenderEvaluator implements OfflineRecommenderEvaluator {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectDetailPageViewService pageViewService;
	
	@Autowired
	private MahoutColaborativeFilteringService mahoutColaborativeFilteringService;
	
	@Value("${recommender.results.sample.max}")
	private int howManyItems;

	@Override
	public void evaluate() throws TasteException {
		List<UserEntity> users = userService.findAll();
		if (users != null) {
			for (UserEntity user : users) {
				
				int successfulRecommendations = 0;
				int failedRecommendations = 0;
				
				List<OpenHubProjectEntity> projectsViewed = pageViewService.findAllProjectRecentlyViewedByUser(user);
				
				
				if (projectsViewed != null && projectsViewed.size() >= 2) {
					
					List<Long> ids = new ArrayList<Long>();
					for (OpenHubProjectEntity project : projectsViewed) {
						ids.add(project.getId());
					}
										
					for (OpenHubProjectEntity project : projectsViewed) {
						List<RecommendedItem> recommendedItems = 
								mahoutColaborativeFilteringService.recommendViewedProjectsByItem(project.getId(), howManyItems);
						
						if (recommendedItems != null) {
							boolean found = false;;
							for (RecommendedItem recommended : recommendedItems) {
								if (! project.getId().equals(recommended.getItemID()) && ids.contains(Long.valueOf(recommended.getItemID()))) {
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
					}
					
					int total = successfulRecommendations + failedRecommendations;
					
					System.out.println(
							String.format("User: %02d | Projects Viewed: %d \t| Successful Recommendations: %d (%.2f%%) \t| Failed Recommendations: %2d (%.2f%%)", 
									user.getId(),
									projectsViewed.size(),
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
