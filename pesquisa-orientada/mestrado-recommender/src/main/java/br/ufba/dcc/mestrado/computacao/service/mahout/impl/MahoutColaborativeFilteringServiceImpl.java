
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
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.AllUnknownItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.RandomRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.MostSimilarItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
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

	@Override
	public RecommenderBuilder getUserBasedRecommenderBuilder() {
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
				
				UserSimilarity similarity = new LogLikelihoodSimilarity(dataModel);
				
				double threshold = 0.5;
				UserNeighborhood neighborhood = new ThresholdUserNeighborhood(threshold, similarity, dataModel);
				
				Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
				
				return recommender;
			}
		};
		return recommenderBuilder;
	}
	
	@Override
	public RecommenderBuilder getBooleanItemBasedRecommenderBuilder() {
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
		return recommenderBuilder;
	}
	
	@Override
	public RecommenderBuilder getBooleanUserBasedRecommenderBuilder() {
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
				
				UserSimilarity userSimilarity = new LogLikelihoodSimilarity(dataModel);
				
				double threshold = 0.5;
				UserNeighborhood userNeighborhood = new ThresholdUserNeighborhood(threshold, userSimilarity, dataModel);
				
				Recommender recommender = new GenericBooleanPrefUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
				return recommender;
			}
		};
		return recommenderBuilder;
	}

	
	
	@Override
	@Transactional(readOnly = true)
	protected List<RecommendedItem> recommendViewedProjectsByUser(
			Long userId, 
			Integer howManyItems,
			boolean filterInterestTags,
			List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList) {
		
		List<RecommendedItem> recommendedItemList  = null;
		List<BooleanPreference> preferenceList = null;
		
		if (pageViewList != null && ! pageViewList.isEmpty()) {
			preferenceList = new ArrayList<>();
			
			for (ImmutablePair<UserEntity, OpenHubProjectEntity> pair : pageViewList) {
				BooleanPreference preference = new BooleanPreference(pair.getLeft().getId(), pair.getRight().getId());
				preferenceList.add(preference);
			}
		}
		
		if (preferenceList != null && ! preferenceList.isEmpty()) {
			final GenericBooleanPrefDataModel dataModel = mahoutDataModelService.buildBooleanDataModel(preferenceList);
			
			RecommenderBuilder recommenderBuilder = getBooleanUserBasedRecommenderBuilder();
			
			try {
				GenericBooleanPrefUserBasedRecommender recommender = (GenericBooleanPrefUserBasedRecommender) recommenderBuilder.buildRecommender(dataModel);
				
				
				if (filterInterestTags) {
				
					IDRescorer idRescorer = buildIdRescorer(userId);
					
					//aplicando filtro de tags no recomendador
					recommendedItemList = recommender.recommend(userId, howManyItems, idRescorer);
				} else {
					recommendedItemList = recommender.recommend(userId, howManyItems);
				}
				
			} catch (TasteException e) {
				e.printStackTrace();
			}
		}
		
		
		return recommendedItemList;
	}


	@Override
	protected List<RecommendedItem> recommendRatingProjectsByUser(
			Long userId,
			Integer howManyItems,
			boolean filterInterestTags,
			Map<Long, Map<ImmutablePair<Long, Long>, Double>> ratingsMap) {
		
		throw new NotImplementedException("Ainda não implementei o método para recomendar utilizando as avaliações multicritério dos outros usuários");
	}
	
	@Override
	@Transactional(readOnly = true)
	protected List<RecommendedItem> recommendViewedProjectsByItem(
			Long itemId, 
			Integer howManyItems,
			List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList) {
		
		List<RecommendedItem> recommendedItemList = null;
		
		List<BooleanPreference> preferenceList = null;
		
		if (pageViewList != null && ! pageViewList.isEmpty()) {
			preferenceList = new ArrayList<>();
			
			for (ImmutablePair<UserEntity, OpenHubProjectEntity> pair : pageViewList) {
				BooleanPreference preference = new BooleanPreference(pair.getLeft().getId(), pair.getRight().getId());
				preferenceList.add(preference);
			}
		}
		
		RecommenderBuilder recommenderBuilder = getBooleanItemBasedRecommenderBuilder();
		
		if (preferenceList != null && ! preferenceList.isEmpty()) {
			final GenericBooleanPrefDataModel dataModel = mahoutDataModelService.buildBooleanDataModel(preferenceList);
			
			try {
				GenericBooleanPrefItemBasedRecommender recommender = (GenericBooleanPrefItemBasedRecommender) recommenderBuilder.buildRecommender(dataModel);
				recommendedItemList  = recommender.mostSimilarItems(itemId, howManyItems);
			} catch (TasteException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return recommendedItemList;
	}

	@Override
	protected List<RecommendedItem> recommendRatingProjectsByUserAndCriterium(
			Long userId, 
			Long criteriumId, 
			Integer howManyItems,
			boolean filterInterestTags,
			Map<ImmutablePair<Long, Long>, Double> ratingsMap) {
		
		List<RecommendedItem> recommendedItemList  = null;
		
		List<Preference> preferenceList = null;
		
		if (ratingsMap != null) {
			preferenceList = new ArrayList<Preference>();
			
			for (ImmutablePair<Long, Long> userItemPair : ratingsMap.keySet()) {
				
				Double preferenceValue = ratingsMap.get(userItemPair);
				
				Preference preference = new GenericPreference(
						userItemPair.getLeft(),
						userItemPair.getRight(),
						preferenceValue.floatValue()
						);
				
				preferenceList.add(preference);
			}
			
			
			RecommenderBuilder recommenderBuilder = getUserBasedRecommenderBuilder();
			
			if (preferenceList != null) {
				final GenericDataModel dataModel = mahoutDataModelService.buildDataModelByUser(preferenceList);
				
				try {
					GenericUserBasedRecommender recommender = (GenericUserBasedRecommender) recommenderBuilder.buildRecommender(dataModel);
					
					
					if (filterInterestTags) {
						IDRescorer idRescorer = buildIdRescorer(userId);
						
						//aplicando filtro de tags no recomendador
						recommendedItemList = recommender.recommend(userId, howManyItems, idRescorer);
					} else {
						recommendedItemList = recommender.recommend(userId, howManyItems);
					}
					
				} catch (TasteException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
		return recommendedItemList;
	}


	@Override
	protected List<RecommendedItem> recommendRandomProjectsByUser(
			Long userId, 
			Integer howManyItems, 
			boolean filterInterestTags,
			Map<ImmutablePair<Long, Long>, Double> ratingsMap) {
		
		List<RecommendedItem> recommendedItemList  = null;
		
		List<Preference> preferenceList = null;
		
		
		if (ratingsMap != null) {
			preferenceList = new ArrayList<Preference>();
			
			for (ImmutablePair<Long, Long> userItemPair : ratingsMap.keySet()) {
				
				Double preferenceValue = ratingsMap.get(userItemPair);
				
				Preference preference = new GenericPreference(
						userItemPair.getLeft(),
						userItemPair.getRight(),
						preferenceValue.floatValue()
						);
				
				preferenceList.add(preference);
			}
			
			RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
				@Override
				public Recommender buildRecommender(DataModel dataModel) throws TasteException {
					
					Recommender recommender = new RandomRecommender(dataModel);					
					return recommender;
				}
			};
			
			if (preferenceList != null) {
				final GenericDataModel dataModel = mahoutDataModelService.buildDataModelByUser(preferenceList);
				
				try {
					RandomRecommender recommender = (RandomRecommender) recommenderBuilder.buildRecommender(dataModel);
					
					
					if (filterInterestTags) {
						IDRescorer idRescorer = buildIdRescorer(userId);
						
						//aplicando filtro de tags no recomendador
						recommendedItemList = recommender.recommend(userId, howManyItems, idRescorer);
					} else {
						recommendedItemList = recommender.recommend(userId, howManyItems);
					}
					
				} catch (TasteException e) {
					e.printStackTrace();
				}
			}
		}
		
		return recommendedItemList;
	}

	
	

}

