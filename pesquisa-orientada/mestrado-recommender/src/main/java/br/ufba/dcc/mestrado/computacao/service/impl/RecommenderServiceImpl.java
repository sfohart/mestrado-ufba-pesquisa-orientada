package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;
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
import org.apache.mahout.cf.taste.impl.recommender.PreferredItemsNeighborhoodCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.similarity.CachingItemSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
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
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.UserRecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.recommender.impl.MultiCriteriaRecommenderImpl;
import br.ufba.dcc.mestrado.computacao.recommender.impl.WeightedAverageAggregatorStrategy;
import br.ufba.dcc.mestrado.computacao.repository.base.CriteriumPreferenceRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.UserRecommenderCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderService;

@Service
public class RecommenderServiceImpl implements RecommenderService {

	private Logger logger = Logger.getLogger(RecommenderServiceImpl.class.getName());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -541832144049879214L;
	
	private CriteriumPreferenceRepository criteriumPreferenceRepository;
	private RecommenderCriteriumRepository recommenderCriteriumRepository;
	private UserRecommenderCriteriumRepository userRecommenderCriteriumRepository;
	
	/**
	 * 
	 * @author leandro.ferreira
	 *
	 */
	protected class RecommenderItemDataModelCallable implements Callable<DataModel> {
		private Long criteriumID;
		
		public RecommenderItemDataModelCallable(Long criteriumID) {
			this.criteriumID = criteriumID;
		}

		@Override
		public DataModel call() throws Exception {			
			return buildItemDataModel(criteriumID);
		}
	}
	
	/**
	 * 
	 * @author leandro.ferreira
	 *
	 */
	protected class RecommenderUserDataModelCallable implements Callable<DataModel> {
		private Long criteriumID;
		
		public RecommenderUserDataModelCallable(Long criteriumID) {
			this.criteriumID = criteriumID;
		}

		@Override
		public DataModel call() throws Exception {			
			return buildUserDataModel(criteriumID);
		}
	}
	
	
	public RecommenderServiceImpl(
			CriteriumPreferenceRepository criteriumPreferenceRepository,
			RecommenderCriteriumRepository recommenderCriteriumRepository,
			UserRecommenderCriteriumRepository userRecommenderCriteriumRepository) {
		this.criteriumPreferenceRepository = criteriumPreferenceRepository;
		this.recommenderCriteriumRepository = recommenderCriteriumRepository;
		this.userRecommenderCriteriumRepository = userRecommenderCriteriumRepository;
	}

	public CriteriumPreferenceRepository getCriteriumPreferenceRepository() {
		return criteriumPreferenceRepository;
	}
	
	public RecommenderCriteriumRepository getRecommenderCriteriumRepository() {
		return recommenderCriteriumRepository;
	}
	
	public UserRecommenderCriteriumRepository getUserRecommenderCriteriumRepository() {
		return userRecommenderCriteriumRepository;
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
				
				UserSimilarity baseUserSimilarity = new PearsonCorrelationSimilarity(dataModel);
				UserSimilarity userSimilarity = new CachingUserSimilarity(baseUserSimilarity, dataModel);
				
				UserNeighborhood baseUserNeighborhood = new NearestNUserNeighborhood(10, 0.7, userSimilarity, dataModel);
				UserNeighborhood userNeighborhood = new CachingUserNeighborhood(baseUserNeighborhood, dataModel);
				
				Recommender baseRecommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
				Recommender recommender = new CachingRecommender(baseRecommender);
				return recommender;
			}
		};
		
		return recommenderBuilder;
	}


	public RecommenderBuilder createItemBasedRecomenderBuilder(DataModel dataModel) {
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
				
				ItemSimilarity baseItemSimilarity = new PearsonCorrelationSimilarity(dataModel);
				ItemSimilarity itemSimilarity = new CachingItemSimilarity(baseItemSimilarity, dataModel);
				
				CandidateItemsStrategy candidateItemsStrategy = new AllUnknownItemsCandidateItemsStrategy();
				MostSimilarItemsCandidateItemsStrategy mostSimilarItemsCandidateItemsStrategy = new AllUnknownItemsCandidateItemsStrategy();
				
				Recommender baseRecommender = new GenericItemBasedRecommender(dataModel, itemSimilarity, candidateItemsStrategy, mostSimilarItemsCandidateItemsStrategy);
				Recommender recommender = new CachingRecommender(baseRecommender);
				return recommender;
			}
		};
		
		return recommenderBuilder;
	}

	protected FastByIDMap<DataModel> doDataModelMap(List<RecommenderCriteriumEntity> criteriaList) {
		ExecutorService executor = Executors.newFixedThreadPool(criteriaList.size());

		Map<Long, FutureTask<DataModel>> futureTaskMap = new TreeMap<>();
		
		FastByIDMap<DataModel> dataModelMap = new FastByIDMap<DataModel>();
		
		if (criteriaList != null) {
			for (RecommenderCriteriumEntity criterium : criteriaList) {
				RecommenderUserDataModelCallable callable = 
						new RecommenderUserDataModelCallable(criterium.getId());
				
				FutureTask<DataModel> futureTask = new FutureTask<>(callable);				
				futureTaskMap.put(criterium.getId(), futureTask);
				executor.execute(futureTask);
			}
			
			while (true) {
				boolean allDone = true;
				
				for (FutureTask<DataModel> futureTask : futureTaskMap.values()) {
					if (! futureTask.isDone()) {
						allDone = false;
					}
				}
				
				if (allDone) {
					executor.shutdown();
					break;
				}
			}
						
			for (Map.Entry<Long, FutureTask<DataModel>> entry : futureTaskMap.entrySet()) {		
				try {
					DataModel dataModel = entry.getValue().get();
					dataModelMap.put(entry.getKey(), dataModel);
				} catch (ExecutionException ex) {
					ex.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}
		
		return dataModelMap;
	}
	
	protected FastByIDMap<RecommenderBuilder> doRecommenderBuilderMap(FastByIDMap<DataModel> dataModelMap) {
		FastByIDMap<RecommenderBuilder> recommenderBuilderMap = new FastByIDMap<RecommenderBuilder>();
		
		if (dataModelMap != null) {			
						
			for (Map.Entry<Long, DataModel> entry : dataModelMap.entrySet()) {		
				DataModel dataModel = entry.getValue();
				RecommenderBuilder recommenderBuilder = createItemBasedRecomenderBuilder(dataModel);
				
				dataModelMap.put(entry.getKey(), dataModel);
				recommenderBuilderMap.put(entry.getKey(), recommenderBuilder);
			}			
		}
		
		return recommenderBuilderMap;
	}
	
	public MultiCriteriaRecommender buildMultiCriteriaRecommender(Long userId) throws TasteException {
		logger.info(String.format("Building MultiCriteriaRecommender to user '%d'", userId));
		List<UserRecommenderCriteriumEntity> userCriteriaList = getUserRecommenderCriteriumRepository().findAllByUser(userId);
		
		MultiCriteriaRecommender recommender = null;
		
		if (userCriteriaList != null) {			
			List<RecommenderCriteriumEntity> criteriaList = new ArrayList<>();
			FastByIDMap<Float> weightMap = new FastByIDMap<>();
			for (UserRecommenderCriteriumEntity userCriterium : userCriteriaList) {
				criteriaList.add(userCriterium.getCriterium());
				weightMap.put(userCriterium.getCriteriumId(), userCriterium.getWeight());
			}
			
			if (criteriaList != null) {
				FastByIDMap<DataModel> dataModelMap = doDataModelMap(criteriaList);
				FastByIDMap<RecommenderBuilder> recommenderBuilderMap = doRecommenderBuilderMap(dataModelMap);
				
				WeightedAverageAggregatorStrategy aggregatorStrategy = new WeightedAverageAggregatorStrategy(weightMap);
				//CandidateItemsStrategy candidateItemsStrategy = new AllUnknownItemsCandidateItemsStrategy();
				CandidateItemsStrategy candidateItemsStrategy = new PreferredItemsNeighborhoodCandidateItemsStrategy(); 
				recommender = new MultiCriteriaRecommenderImpl(candidateItemsStrategy, dataModelMap, recommenderBuilderMap, aggregatorStrategy);
			}
		}
		
		return recommender;
		
	}
	
	/**
	 * Cria um recomendador multi-dimensional.
	 * Utiliza threads para criar
	 */
	public MultiCriteriaRecommender buildMultiCriteriaRecommender() throws TasteException {
		logger.info("Building default MultiCriteriaRecommender");
		List<RecommenderCriteriumEntity> criteriaList = getRecommenderCriteriumRepository().findAll();
		
		MultiCriteriaRecommender recommender = null;
		
		if (criteriaList != null) {
			FastByIDMap<DataModel> dataModelMap = doDataModelMap(criteriaList);
			FastByIDMap<RecommenderBuilder> recommenderBuilderMap = doRecommenderBuilderMap(dataModelMap);
			
			recommender = new MultiCriteriaRecommenderImpl(dataModelMap, recommenderBuilderMap);		
		}
		
		return recommender;
	}
	
}
