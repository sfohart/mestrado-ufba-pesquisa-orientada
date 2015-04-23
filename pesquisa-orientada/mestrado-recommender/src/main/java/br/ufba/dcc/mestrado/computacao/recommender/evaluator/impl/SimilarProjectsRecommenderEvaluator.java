package br.ufba.dcc.mestrado.computacao.recommender.evaluator.impl;

import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.recommender.evaluator.base.OfflineRecommenderEvaluator;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;

@Component
public class SimilarProjectsRecommenderEvaluator implements OfflineRecommenderEvaluator {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectDetailPageViewService pageViewService;
	
	@Autowired
	private SearchService searchService;

	@Override
	public void evaluate() throws TasteException {
		List<UserEntity> users = userService.findAll();
		if (users != null) {
			for (UserEntity user : users) {
				
				int successfulRecommendations = 0;
				int failedRecommendations = 0;
				
				List<OpenHubProjectEntity> projectsViewed = pageViewService.findAllProjectRecentlyViewedByUser(user);
				
				
				if (projectsViewed != null && projectsViewed.size() >= 2) {
										
					for (OpenHubProjectEntity project : projectsViewed) {
						
						List<OpenHubProjectEntity> similarProjects = null;
						try {
							similarProjects = searchService.findRelatedProjects(project);
						} catch (IOException e) {
							throw new TasteException(e);
						}
						
						if (similarProjects != null) {
							boolean found = false;;
							for (OpenHubProjectEntity recommended : similarProjects) {
								if (! project.equals(recommended) && projectsViewed.contains(recommended) ) {
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
