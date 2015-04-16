package br.ufba.dcc.mestrado.computacao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
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
	
	public BaseColaborativeFilteringService getColaborativeFilteringService() {
		return colaborativeFilteringService;
	}
	
	public void setColaborativeFilteringService(
			BaseColaborativeFilteringService colaborativeFilteringService) {
		this.colaborativeFilteringService = colaborativeFilteringService;
	}
	
	public RecommenderCriteriumService getCriteriumService() {
		return criteriumService;
	}
	
	public void setCriteriumService(RecommenderCriteriumService criteriumService) {
		this.criteriumService = criteriumService;
	}
	
	@Test
	public void testRandomRecommender() {
		Long userId = 11L;
		Integer howManyItems = 10;
		
		List<OpenHubProjectEntity> recommendations = getColaborativeFilteringService().recommendRandomProjectsByUser(userId, howManyItems);
		
		Assert.assertNotNull(recommendations);
		
		Collections.sort(recommendations, new Comparator<OpenHubProjectEntity>() {

			@Override
			public int compare(OpenHubProjectEntity o1, OpenHubProjectEntity o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
			
		});
		
		for (OpenHubProjectEntity project : recommendations) {
			System.out.println(String.format("%d - %s", project.getId(), project.getName()));
		}
	}
	
	@Test
	public void testColaborativeFilteringRecommender() {
		
		Long userId = 11L;
		Integer howManyItems = 10;
		
		List<RecommenderCriteriumEntity> criteria = getCriteriumService().findAll();
		
		Assert.assertNotNull(criteria);
		
		for (RecommenderCriteriumEntity criterium : criteria) {
			List<OpenHubProjectEntity> recommendations =
					getColaborativeFilteringService()
						.recommendRatingProjectsByUserAndCriterium(
								userId, 
								criterium.getId(), 
								howManyItems);
			
			Assert.assertNotNull(recommendations);
			
			Collections.sort(recommendations, new Comparator<OpenHubProjectEntity>() {

				@Override
				public int compare(OpenHubProjectEntity o1, OpenHubProjectEntity o2) {
					return o1.getName().compareToIgnoreCase(o2.getName());
				}
				
			});
			
			System.out.println(criterium.getName() + " ----------------------------------------------");
			for (OpenHubProjectEntity project : recommendations) {
				System.out.println(String.format("%d - %s", project.getId(), project.getName()));
			}
		}
		
		
		
	}
	
}