package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericItemPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.CachingUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.AllUnknownItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingItemSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.MostSimilarItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.repository.base.CriteriumPreferenceRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderService;

@Service
public class RecommenderServiceImpl implements RecommenderService {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -541832144049879214L;
	
	private CriteriumPreferenceRepository criteriumPreferenceRepository;
	private RecommenderCriteriumRepository recommenderCriteriumRepository;
	
	
	public RecommenderServiceImpl(
			CriteriumPreferenceRepository criteriumPreferenceRepository,
			RecommenderCriteriumRepository recommenderCriteriumRepository) {
		this.criteriumPreferenceRepository = criteriumPreferenceRepository;
		this.recommenderCriteriumRepository = recommenderCriteriumRepository;
	}

	public CriteriumPreferenceRepository getCriteriumPreferenceRepository() {
		return criteriumPreferenceRepository;
	}
	
	public RecommenderCriteriumRepository getRecommenderCriteriumRepository() {
		return recommenderCriteriumRepository;
	}
	
	public Long countAllByCriterium(Long criteriumID) {
		return criteriumPreferenceRepository.countAllByCriterium(criteriumID);
	}

	public List<Preference> findAllByCriterium(Long criteriumID) {
		return getCriteriumPreferenceRepository().findAllByCriterium(criteriumID);
	}

	public List<Preference> findAllByCriterium(Long criteriumID,
			Integer limit, Integer offset) {
		return getCriteriumPreferenceRepository().findAllByCriterium(criteriumID, limit, offset);
	}

	public DataModel buildUserDataModel(Long criteriumID) {
		List<Preference> preferencesByCriterium = findAllByCriterium(criteriumID);
		
		Map<Long, List<Preference>> userPreferenceMap = new HashMap<>();
		
		if (preferencesByCriterium != null) {
			for (Preference pref : preferencesByCriterium) {
				List<Preference> preferenceList = userPreferenceMap.get(pref.getUserID());
				
				if (preferenceList == null) {
					preferenceList = new ArrayList<>();
				}
				
				preferenceList.add(pref);
				
				userPreferenceMap.put(pref.getUserID(), preferenceList);
			}
		}
		
		FastByIDMap<PreferenceArray> userData = new FastByIDMap<>(userPreferenceMap.size());
		for (Map.Entry<Long, List<Preference>> entry : userPreferenceMap.entrySet()) {
			PreferenceArray array = new GenericUserPreferenceArray(entry.getValue());
			userData.put(entry.getKey(), array);
		}
		
		DataModel dataModel = new GenericDataModel(userData);
		
		return dataModel;
	}
	
	public DataModel buildItemDataModel(Long criteriumID) {
		List<Preference> preferencesByCriterium = findAllByCriterium(criteriumID);
		
		Map<Long, List<Preference>> itemPreferenceMap = new HashMap<>();
		
		if (preferencesByCriterium != null) {
			for (Preference pref : preferencesByCriterium) {
				List<Preference> preferenceList = itemPreferenceMap.get(pref.getItemID());
				
				if (preferenceList == null) {
					preferenceList = new ArrayList<>();
				}
				
				preferenceList.add(pref);
				
				itemPreferenceMap.put(pref.getItemID(), preferenceList);
			}
		}
		
		FastByIDMap<PreferenceArray> itemData = new FastByIDMap<>(itemPreferenceMap.size());
		for (Map.Entry<Long, List<Preference>> entry : itemPreferenceMap.entrySet()) {
			PreferenceArray array = new GenericItemPreferenceArray(entry.getValue());
			itemData.put(entry.getKey(), array);
		}
		
		DataModel dataModel = new GenericDataModel(itemData);
		
		return dataModel;
	}
	
	public RecommenderBuilder createUserBasedRecomenderBuilder(DataModel dataModel) {
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
				
				UserSimilarity baseUserSimilarity = new LogLikelihoodSimilarity(dataModel);
				UserSimilarity userSimilarity = new CachingUserSimilarity(baseUserSimilarity, dataModel);
				
				UserNeighborhood baseUserNeighborhood = new NearestNUserNeighborhood(10, 0.7, userSimilarity, dataModel);
				UserNeighborhood userNeighborhood = new CachingUserNeighborhood(baseUserNeighborhood, dataModel);
				
				Recommender baseRecommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
				return baseRecommender;
			}
		};
		
		return recommenderBuilder;
	}


	public RecommenderBuilder createItemBasedRecomenderBuilder(DataModel dataModel) {
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
				
				ItemSimilarity baseItemSimilarity = new LogLikelihoodSimilarity(dataModel);
				ItemSimilarity itemSimilarity = new CachingItemSimilarity(baseItemSimilarity, dataModel);
				
				CandidateItemsStrategy candidateItemsStrategy = new AllUnknownItemsCandidateItemsStrategy();
				MostSimilarItemsCandidateItemsStrategy mostSimilarItemsCandidateItemsStrategy = new AllUnknownItemsCandidateItemsStrategy();
				
				Recommender baseRecommender = new GenericItemBasedRecommender(dataModel, itemSimilarity, candidateItemsStrategy, mostSimilarItemsCandidateItemsStrategy);
				return baseRecommender;
			}
		};
		
		return recommenderBuilder;
	}

	public MultiCriteriaRecommender buildMultiCriteriaRecommender() throws TasteException {
		List<RecommenderCriteriumEntity> criteriaList = getRecommenderCriteriumRepository().findAll();
		
		Map<Long, Recommender> recommenderMap = new HashMap<>();
		if (criteriaList != null) {
			for (RecommenderCriteriumEntity criterium : criteriaList) {
				DataModel dataModel = buildUserDataModel(criterium.getId());
				RecommenderBuilder recommenderBuilder = createItemBasedRecomenderBuilder(dataModel);
				
				Recommender recommender = recommenderBuilder.buildRecommender(dataModel);
				recommenderMap.put(criterium.getId(), recommender);
			}
		}
		
		MultiCriteriaRecommender multiCriteriaRecommender = new MultiCriteriaRecommender(recommenderMap);
		return multiCriteriaRecommender;
	}
	
}
