package br.ufba.dcc.mestrado.computacao.recommender.evaluator.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;
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
	
	private static final Logger logger = Logger.getLogger(AbstractMultiCriteriaRecommenderEvaluator.class);

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
	
	protected abstract MultiCriteriaRecommender buildMultiCriteriaRecommender(RecommenderBuilder recommenderBuilder) throws TasteException;
	
	@Override
	public void evaluate() throws TasteException {
		
		RecommenderBuilder recommenderBuilder = colaborativeFilteringService.getUserBasedRecommenderBuilder();
		
		MultiCriteriaRecommender multiCriteriaRecommender = buildMultiCriteriaRecommender(recommenderBuilder);		
		List<UserEntity> users = userService.findAll();
		
		Collections.sort(users, new Comparator<UserEntity>() {
			@Override
			public int compare(UserEntity o1, UserEntity o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});
		
		if (users != null) {
			listRatedAndViewedProjects(users);
			evaluateRecommendations(multiCriteriaRecommender, users);
		}
		
	}

	protected void listRatedAndViewedProjects(List<UserEntity> users) {
		if (users != null) {
			logger.info("Listando projetos visualizados e avaliados pelos usuarios");
			for (UserEntity user : users) {
				List<OpenHubProjectEntity> ratedProjects = overallRatingService.findAllRatedProjectsByUser(user.getId());
				List<OpenHubProjectEntity> viewedProjects = pageViewService.findAllProjectRecentlyViewedByUser(user);
				
				logger.info(String.format("Listando informações do usuário %d", user.getId()));
				
				if (! viewedProjects.isEmpty()){
					logger.info(String.format("Listando projetos visualizados pelo usuário %d", user.getId()));
					System.out.println("Id do Projeto; Nome do Projeto");
					for (OpenHubProjectEntity project : viewedProjects) {
						System.out.println(String.format("%d;%s", project.getId(), project.getName()));
					}
				}
			
				if (! ratedProjects.isEmpty()) {
					logger.info(String.format("Listando projetos avaliados pelo usuário %d", user.getId()));
					System.out.println("Id do Projeto; Nome do Projeto");
					for (OpenHubProjectEntity project : ratedProjects) {
						System.out.println(String.format("%d;%s", project.getId(), project.getName()));
					}
				}
			}
		}
		
	}

	protected void evaluateRecommendations(
			MultiCriteriaRecommender multiCriteriaRecommender,
			List<UserEntity> users) throws TasteException {
		
		if (users != null) {
			System.out.println("Id do usuario;Projetos Visualizados;Projetos avaliados;Projetos recomendados;Projetos recomendados vistos, mas nao avaliados;Projetos recomendados nao vistos ou ja avaliados");
			for (UserEntity user : users) {				
				int successfulRecommendations = 0;
				int failedRecommendations = 0;
				
				List<OpenHubProjectEntity> ratedProjects = overallRatingService.findAllRatedProjectsByUser(user.getId());
				List<OpenHubProjectEntity> viewedProjects = pageViewService.findAllProjectRecentlyViewedByUser(user);
				
				if (ratedProjects != null && ratedProjects.size() >= 2) {
					
					List<Long> viewedIds = new ArrayList<Long>();
					for (OpenHubProjectEntity project : viewedProjects) {
						viewedIds.add(project.getId());
					}
					
					List<Long> ratedIds = new ArrayList<Long>();
					for (OpenHubProjectEntity project : ratedProjects) {
						ratedIds.add(project.getId());
					}
										
					List<RecommendedItem> recommendedItems = multiCriteriaRecommender.recommend(user.getId(), howManyItems);
					
					if (recommendedItems != null) {
						for (RecommendedItem recommended : recommendedItems) {
							if (viewedIds.contains(Long.valueOf(recommended.getItemID()))) {
								if (! ratedIds.contains(Long.valueOf(recommended.getItemID()))) {
									successfulRecommendations++;
								} else {
									failedRecommendations++;
								}
							} else {
								failedRecommendations++;
							}
						}
					}
					
					String line = String.format(
							"%02d;%d;%d;%d;%d;%d"
							,user.getId()
							,viewedProjects.size()
							,ratedProjects.size()
							,recommendedItems.size()
							,successfulRecommendations
							,failedRecommendations
							);
					
					System.out.println(line);
				}
				
			}
		}
	}

}
