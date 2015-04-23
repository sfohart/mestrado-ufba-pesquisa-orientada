package br.ufba.dcc.mestrado.computacao;

import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.core.base.BaseColaborativeFilteringService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.mahout.impl.MahoutColaborativeFilteringServiceImpl;
import br.ufba.dcc.mestrado.computacao.spring.RecommenderAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RecommenderAppConfig.class})
public class ColaborativeFilteringTests {

	@Autowired
	@Qualifier(MahoutColaborativeFilteringServiceImpl.BEAN_NAME)
	private BaseColaborativeFilteringService colaborativeFilteringService;
	
	@Autowired
	private RecommenderCriteriumService criteriumService;
	
	@Autowired
	private ProjectService projectService;
	
	public BaseColaborativeFilteringService getColaborativeFilteringService() {
		return colaborativeFilteringService;
	}

	public RecommenderCriteriumService getCriteriumService() {
		return criteriumService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	@Test
	public void testColaborativeFilteringRecommender() {
		
		Long userId = 11L;
		Integer howManyItems = 10;
		
		List<RecommenderCriteriumEntity> criteria = getCriteriumService().findAll();
		
		Assert.assertNotNull(criteria);
		
		for (RecommenderCriteriumEntity criterium : criteria) {
			List<RecommendedItem> recommendations =
					getColaborativeFilteringService()
						.recommendRatingProjectsByUserAndCriterium(
								userId, 
								criterium.getId(), 
								howManyItems);
			
			Assert.assertNotNull(recommendations);
			
			System.out.println(criterium.getName() + " ----------------------------------------------");
			for (RecommendedItem recommendedItem : recommendations) {
				if (recommendedItem != null) {
					OpenHubProjectEntity project = projectService.findById(recommendedItem.getItemID());
					System.out.println(String.format("Estimated Preference: %f | Project: \"%s\" (id = %d) ", recommendedItem.getValue(), project.getName(), project.getId()));
				}
			}
		}
		
	}
	
}
