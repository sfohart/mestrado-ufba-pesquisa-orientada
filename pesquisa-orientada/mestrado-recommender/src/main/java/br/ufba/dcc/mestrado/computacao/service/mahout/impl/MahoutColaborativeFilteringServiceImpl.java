
package br.ufba.dcc.mestrado.computacao.service.mahout.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.model.BooleanPreference;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.recommender.AllUnknownItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.MostSimilarItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.core.impl.BaseColaborativeFilteringServiceImpl;
import br.ufba.dcc.mestrado.computacao.service.mahout.base.MahoutColaborativeFilteringService;
import br.ufba.dcc.mestrado.computacao.service.mahout.base.MahoutDataModelService;

@Service(MahoutColaborativeFilteringServiceImpl.BEAN_NAME)
public class MahoutColaborativeFilteringServiceImpl
		extends BaseColaborativeFilteringServiceImpl
		implements MahoutColaborativeFilteringService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9171530318540837388L;
	
	public static final String BEAN_NAME =  "mahoutColaborativeFilteringService";
	
	@Autowired
	private MahoutDataModelService mahoutDataModelService;
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;

	public MahoutDataModelService getMahoutDataModelService() {
		return mahoutDataModelService;
	}

	public void setMahoutDataModelService(
			MahoutDataModelService mahoutDataModelService) {
		this.mahoutDataModelService = mahoutDataModelService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	@Override
	@Transactional(readOnly = true)
	protected List<OpenHubProjectEntity> recommendViewedProjectsByUser(
			Long userId, 
			Integer howManyItems,
			boolean filterInterestTags,
			List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList) {
		
		List<OpenHubProjectEntity> recommendedProjectList = null;
		List<BooleanPreference> preferenceList = null;
		
		if (pageViewList != null && ! pageViewList.isEmpty()) {
			preferenceList = new ArrayList<>();
			
			for (ImmutablePair<UserEntity, OpenHubProjectEntity> pair : pageViewList) {
				BooleanPreference preference = new BooleanPreference(pair.getLeft().getId(), pair.getRight().getId());
				preferenceList.add(preference);
			}
		}
		
		if (preferenceList != null && ! preferenceList.isEmpty()) {
			final GenericBooleanPrefDataModel dataModel = getMahoutDataModelService().buildBooleanDataModel(preferenceList);
			
			RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
				@Override
				public Recommender buildRecommender(DataModel dataModel) throws TasteException {
					
					ItemSimilarity itemSimilarity = new LogLikelihoodSimilarity(dataModel);
					
					CandidateItemsStrategy candidateItemsStrategy = new AllUnknownItemsCandidateItemsStrategy();
					MostSimilarItemsCandidateItemsStrategy mostSimilarItemsCandidateItemsStrategy = new AllUnknownItemsCandidateItemsStrategy();
					
					Recommender recommender = new GenericBooleanPrefItemBasedRecommender(dataModel, itemSimilarity, candidateItemsStrategy, mostSimilarItemsCandidateItemsStrategy);
					return recommender;
				}
			};
			
			try {
				GenericBooleanPrefItemBasedRecommender recommender = (GenericBooleanPrefItemBasedRecommender) recommenderBuilder.buildRecommender(dataModel);
				
				final UserEntity currentUser = getUserService().findById(userId);
				
				
				List<RecommendedItem> recommendedItemList  = null;
				if (filterInterestTags) {
				
					IDRescorer idRescorer = new IDRescorer() {
						
						@Override
						public double rescore(long id, double originalScore) {
							return originalScore;
						}
						
						@Override
						public boolean isFiltered(long id) {
							OpenHubProjectEntity project = getProjectService().findById(id);
							if (currentUser.getInterestTags() != null && ! currentUser.getInterestTags().isEmpty()) {
								for (OpenHubTagEntity tag : currentUser.getInterestTags()) {
									if (project.getTags().contains(tag)) {
										return false;
									}
								}
								
								return true;
							} else {
								return false;
							}
						}
					};
					
					//aplicando filtro de tags no recomendador
					recommendedItemList = recommender.recommend(currentUser.getId(), howManyItems, idRescorer);
				} else {
					recommendedItemList = recommender.recommend(currentUser.getId(), howManyItems);
				}
				
				recommendedProjectList = buildProjectList(recommendedItemList);
				
			} catch (TasteException e) {
				e.printStackTrace();
			}
		}
		
		
		return recommendedProjectList;
	}

	@Override
	protected List<OpenHubProjectEntity> recommendRatingProjectsByUser(
			Long userId,
			Integer howManyItems,
			boolean filterInterestTags,
			Map<Long, Map<ImmutablePair<Long, Long>, Double>> ratingsMap) {
		
		throw new NotImplementedException("Ainda não implementei o método para recomendar utilizando as avaliações multicritério dos outros usuários");
	}
	
	@Override
	@Transactional(readOnly = true)
	protected List<OpenHubProjectEntity> recommendViewedProjectsByItem(
			Long itemId, 
			Integer howManyItems,
			List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList) {
		
		List<OpenHubProjectEntity> recommendedProjectList = null;
		
		List<BooleanPreference> preferenceList = null;
		
		if (pageViewList != null && ! pageViewList.isEmpty()) {
			preferenceList = new ArrayList<>();
			
			for (ImmutablePair<UserEntity, OpenHubProjectEntity> pair : pageViewList) {
				BooleanPreference preference = new BooleanPreference(pair.getLeft().getId(), pair.getRight().getId());
				preferenceList.add(preference);
			}
		}
		
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
				
				ItemSimilarity itemSimilarity = new LogLikelihoodSimilarity(dataModel);
				
				CandidateItemsStrategy candidateItemsStrategy = new AllUnknownItemsCandidateItemsStrategy();
				MostSimilarItemsCandidateItemsStrategy mostSimilarItemsCandidateItemsStrategy = new AllUnknownItemsCandidateItemsStrategy();
				
				Recommender recommender = new GenericBooleanPrefItemBasedRecommender(dataModel, itemSimilarity, candidateItemsStrategy, mostSimilarItemsCandidateItemsStrategy);
				return recommender;
			}
		};
		
		if (preferenceList != null && ! preferenceList.isEmpty()) {
			final GenericBooleanPrefDataModel dataModel = getMahoutDataModelService().buildBooleanDataModel(preferenceList);
			
			try {
				GenericBooleanPrefItemBasedRecommender recommender = (GenericBooleanPrefItemBasedRecommender) recommenderBuilder.buildRecommender(dataModel);
				List<RecommendedItem> recommendedItemList  = recommender.mostSimilarItems(itemId, howManyItems);
				
				recommendedProjectList = buildProjectList(recommendedItemList);
				
			} catch (TasteException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return recommendedProjectList;
	}

	protected List<OpenHubProjectEntity> buildProjectList(List<RecommendedItem> recommendedItemList) {
		
		List<OpenHubProjectEntity> recommendedProjectList = null;
		
		if (recommendedItemList != null) {
			recommendedProjectList = new ArrayList<>();
			
			for (RecommendedItem recommendedItem : recommendedItemList) {
				OpenHubProjectEntity recommendedProject = getProjectService().findById(recommendedItem.getItemID());
				recommendedProjectList.add(recommendedProject);
			}
		}
		
		return recommendedProjectList;
	}
	

}

