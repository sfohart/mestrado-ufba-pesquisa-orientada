package br.ufba.dcc.mestrado.computacao.recommender.evaluator.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.recommender.evaluator.base.OfflineRecommenderEvaluator;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;

@Component(SimilarProjectsRecommenderEvaluator.BEAN_NAME)
public class SimilarProjectsRecommenderEvaluator implements OfflineRecommenderEvaluator {
	
	public static final String BEAN_NAME = "similarProjectsRecommenderEvaluator";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectDetailPageViewService pageViewService;
	
	@Autowired
	private SearchService searchService;
	
	public static void main(String[] args) throws TasteException {
		OfflineRecommenderEvaluatorUtils.runEvaluator(SimilarProjectsRecommenderEvaluator.BEAN_NAME);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void evaluate() throws TasteException {
		List<UserEntity> users = userService.findAll();
		
		System.out.println("Id do usuário;Projetos Visualizados;Recomendações com Sucesso;Recomendações com Sucesso (%);Recomendações sem Sucesso;Recomendações sem Sucesso (%)");
		
		if (users != null) {
			for (UserEntity user : users) {
				
				int successfulRecommendations = 0;
				int failedRecommendations = 0;
				
				List<OpenHubProjectEntity> viewedProjects = pageViewService.findAllProjectRecentlyViewedByUser(user);
				
				
				if (viewedProjects != null && viewedProjects.size() >= 2) {
										
					for (OpenHubProjectEntity project : viewedProjects) {
						
						List<OpenHubProjectEntity> similarProjects = null;
						try {
							similarProjects = searchService.findRelatedProjects(project);
						} catch (IOException e) {
							throw new TasteException(e);
						}
						
						if (similarProjects != null && ! similarProjects.isEmpty()) {
							boolean found = false;;
							for (OpenHubProjectEntity recommended : similarProjects) {
								
								if (! project.equals(recommended)) {
									if (viewedProjects.contains(recommended) ) {
										found = true;
										break;
									} else {
										for (OpenHubProjectEntity viewed : viewedProjects) {
											Collection<OpenHubTagEntity> intersection = (Collection<OpenHubTagEntity>) CollectionUtils.intersection(viewed.getTags(), recommended.getTags());
											if (intersection != null && intersection.size() >= 3){
												found = true;
												break;
											}
										}
									}
									
								}
								
							}
							
							if (found) {
								successfulRecommendations++;
							} else {
								failedRecommendations++;
							}
						} else {
							failedRecommendations++;
						}
					}
					
					int total = successfulRecommendations + failedRecommendations;
					
					String line = String.format(
							"%02d;%d;%d;%.2f;%d;%.2f"
							,user.getId()
							,viewedProjects.size()
							,successfulRecommendations
							,(total > 0 ? successfulRecommendations * 100 / total : 0f)
							,failedRecommendations
							,(total > 0 ? failedRecommendations * 100 / total : 0f)
							);
					
					System.out.println(line);
				}
				
			}
		}
		
	}

}
