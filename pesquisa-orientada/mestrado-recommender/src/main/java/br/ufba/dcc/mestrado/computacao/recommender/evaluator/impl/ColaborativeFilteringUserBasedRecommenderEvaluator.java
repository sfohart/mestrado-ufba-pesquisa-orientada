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
import br.ufba.dcc.mestrado.computacao.service.recommender.base.ColaborativeFilteringService;

@Component(ColaborativeFilteringUserBasedRecommenderEvaluator.BEAN_NAME)
public class ColaborativeFilteringUserBasedRecommenderEvaluator implements OfflineRecommenderEvaluator {
	
	public static final String BEAN_NAME = "colaborativeFilteringUserBasedRecommenderEvaluator";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectDetailPageViewService pageViewService;
	
	@Autowired
	private ColaborativeFilteringService colaborativeFilteringService;
	
	@Value("${recommender.results.sample.max}")
	private int howManyItems;

	public static void main(String[] args) throws TasteException {
		OfflineRecommenderEvaluatorUtils.runEvaluator(ColaborativeFilteringUserBasedRecommenderEvaluator.BEAN_NAME);
	}
	
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
					
					List<Long> ids = new ArrayList<Long>();
					for (OpenHubProjectEntity project : viewedProjects) {
						ids.add(project.getId());
					}
										
					List<RecommendedItem> recommendedItems = colaborativeFilteringService.recommendViewedProjectsByUser(user.getId(), howManyItems);
					
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
