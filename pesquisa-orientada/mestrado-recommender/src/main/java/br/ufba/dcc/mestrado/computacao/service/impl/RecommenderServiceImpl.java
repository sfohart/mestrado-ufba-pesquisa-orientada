package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
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
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.MostSimilarItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.recommender.CriteriumPreference;
import br.ufba.dcc.mestrado.computacao.repository.base.CriteriumPreferenceRepository;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderService;

@Service
public class RecommenderServiceImpl implements RecommenderService {

	public Long countAll() {
		return repository.countAll();
	}

	public Long countAllByUser(Long userID) {
		return repository.countAllByUser(userID);
	}

	public Long countAllByItem(Long itemID) {
		return repository.countAllByItem(itemID);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -541832144049879214L;
	
	private CriteriumPreferenceRepository repository;
	
	protected CriteriumPreferenceRepository getRepository() {
		return repository;
	}
	
	@Autowired
	public RecommenderServiceImpl(CriteriumPreferenceRepository repository) {
		this.repository = repository;
	}

	public List<CriteriumPreference> findAll() {
		return repository.findAll();
	}

	public List<CriteriumPreference> findAll(Integer limit, Integer offset) {
		return repository.findAll(limit, offset);
	}

	public List<CriteriumPreference> findAllUser(Long userID) {
		return repository.findAllUser(userID);
	}

	public List<CriteriumPreference> findAllUser(Long userID, Integer limit,
			Integer offset) {
		return repository.findAllUser(userID, limit, offset);
	}

	public List<CriteriumPreference> findAllByItem(Long itemID) {
		return repository.findAllByItem(itemID);
	}

	public List<CriteriumPreference> findAllByItem(Long itemID, Integer limit,
			Integer offset) {
		return repository.findAllByItem(itemID, limit, offset);
	}

	public Long countAllByCriterium(Long criteriumID) {
		return repository.countAllByCriterium(criteriumID);
	}

	public List<CriteriumPreference> findAllByCriterium(Long criteriumID) {
		return repository.findAllByCriterium(criteriumID);
	}

	public List<CriteriumPreference> findAllByCriterium(Long criteriumID,
			Integer limit, Integer offset) {
		return repository.findAllByCriterium(criteriumID, limit, offset);
	}

	public DataModel buildUserDataModel(List<CriteriumPreference> criteriumPreferences) {
		Map<Long, List<CriteriumPreference>> userPreferenceMap = new HashMap<>();
		
		if (criteriumPreferences != null) {
			for (CriteriumPreference pref : criteriumPreferences) {
				List<CriteriumPreference> preferenceList = userPreferenceMap.get(pref.getUserID());
				
				if (preferenceList == null) {
					preferenceList = new ArrayList<>();
				}
				
				preferenceList.add(pref);
				
				userPreferenceMap.put(pref.getUserID(), preferenceList);
			}
		}
		
		FastByIDMap<PreferenceArray> userData = new FastByIDMap<>(userPreferenceMap.size());
		for (Map.Entry<Long, List<CriteriumPreference>> entry : userPreferenceMap.entrySet()) {
			PreferenceArray array = new GenericUserPreferenceArray(entry.getValue());
			userData.put(entry.getKey(), array);
		}
		
		DataModel dataModel = new GenericDataModel(userData);
		
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
				return new CachingRecommender(baseRecommender);
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
				return new CachingRecommender(baseRecommender);
			}
		};
		
		return recommenderBuilder;
	}
	
}
