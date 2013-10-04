package br.ufba.dcc.mestrado.computacao.recommender.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.Cache;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.common.RefreshHelper;
import org.apache.mahout.cf.taste.impl.common.Retriever;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.impl.recommender.PreferredItemsNeighborhoodCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.recommender.TopItems;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.common.LongPair;

import br.ufba.dcc.mestrado.computacao.exception.NotSupportedException;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.recommender.PreferenceAggregatorStrategy;

import com.google.common.base.Preconditions;

/**
 * 
 * @author leandro.ferreira
 *
 */
public class MultiCriteriaRecommenderImpl implements MultiCriteriaRecommender {
	
	private Logger logger = Logger.getLogger(MultiCriteriaRecommenderImpl.class.getName());
	
	private Map<Long,Recommender> recommenderMap;	
	private PreferenceAggregatorStrategy preferenceAggregatorStrategy;
	
	private Cache<LongPair,Float> estimatedPreferenceCache;
	private Cache<Long,Recommendations> recommendationCache;
	
	private Integer maxHowMany;
	private IDRescorer currentRescorer;
	
	private Retriever<Long, Recommendations> recommendationsRetriever;
	private RefreshHelper refreshHelper;
	
	private DataModel overallDataModel;
	private CandidateItemsStrategy candidateItemsStrategy;
	
	/**
	 * 
	 * @param recommenderMap
	 */
	public MultiCriteriaRecommenderImpl(
			Map<Long,DataModel> dataModelMap,
			Map<Long,RecommenderBuilder> recommenderBuilderMap) throws TasteException {
		this   (MultiCriteriaRecommenderImpl.getDefaultCandidateItemsStrategy(),
				dataModelMap,
				recommenderBuilderMap,
				new AveragePreferenceAggregatorStrategy());		
	}
	
	/**
	 * 
	 * @param dataModelMap
	 * @param recommenderBuilder
	 * @throws TasteException
	 */
	public MultiCriteriaRecommenderImpl(
			Map<Long,DataModel> dataModelMap,
			RecommenderBuilder recommenderBuilder) throws TasteException {
		this   (MultiCriteriaRecommenderImpl.getDefaultCandidateItemsStrategy(),
				dataModelMap,
				recommenderBuilder,
				new AveragePreferenceAggregatorStrategy());		
	}
	
	
	/**
	 * 
	 * @param candidateItemsStrategy
	 * @param recommenderMap
	 */
	public MultiCriteriaRecommenderImpl(
			CandidateItemsStrategy candidateItemsStrategy,			
			Map<Long,DataModel> dataModelMap,
			Map<Long,RecommenderBuilder> recommenderBuilderMap) throws TasteException {
		this   (candidateItemsStrategy,				
				dataModelMap,
				recommenderBuilderMap,
				new AveragePreferenceAggregatorStrategy());		
	}
	
	/**
	 * 
	 * @param candidateItemsStrategy
	 * @param dataModelMap
	 * @param recommenderBuilderMap
	 * @throws TasteException
	 */
	public MultiCriteriaRecommenderImpl(
			CandidateItemsStrategy candidateItemsStrategy,			
			Map<Long,DataModel> dataModelMap,
			RecommenderBuilder recommenderBuilder) throws TasteException {
		this   (candidateItemsStrategy,				
				dataModelMap,
				recommenderBuilder,
				new AveragePreferenceAggregatorStrategy());		
	}
	
	/**
	 * 
	 * @param candidateItemsStrategy
	 * @param dataModelMap
	 * @param recommenderBuilderMap
	 * @param preferenceAggregatorStrategy
	 * @throws TasteException
	 */
	public MultiCriteriaRecommenderImpl(
			CandidateItemsStrategy candidateItemsStrategy,
			Map<Long,DataModel> dataModelMap,
			RecommenderBuilder recommenderBuilder,
			PreferenceAggregatorStrategy preferenceAggregatorStrategy) throws TasteException {
		
		this.candidateItemsStrategy = candidateItemsStrategy;
		
		
		this.preferenceAggregatorStrategy = preferenceAggregatorStrategy;
		this.maxHowMany = 1;
		
		initializeRecommenders(dataModelMap, recommenderBuilder);
		initializeCache();		
		initializeRefreshHelper();
	}
	
	/**
	 * 
	 * @param candidateItemsStrategy
	 * @param recommenderMap
	 * @param preferenceAggregatorStrategy
	 * @throws TasteException 
	 */
	public MultiCriteriaRecommenderImpl(
			CandidateItemsStrategy candidateItemsStrategy,
			Map<Long,DataModel> dataModelMap,
			Map<Long,RecommenderBuilder> recommenderBuilderMap,
			PreferenceAggregatorStrategy preferenceAggregatorStrategy) throws TasteException {
		
		this.candidateItemsStrategy = candidateItemsStrategy;
		
		
		this.preferenceAggregatorStrategy = preferenceAggregatorStrategy;
		this.maxHowMany = 1;
		
		initializeRecommenders(dataModelMap, recommenderBuilderMap);
		initializeCache();		
		initializeRefreshHelper();
	}

	/**
	 * 
	 */
	protected void initializeCache() {
		int numUsers = 0;
		for (Map.Entry<Long,Recommender> entry : recommenderMap.entrySet()) {
			try {
				numUsers = entry.getValue().getDataModel().getNumUsers();
				if (numUsers != 0) {
					break;
				}
			} catch (TasteException e) {
				e.printStackTrace();
			}
		}
		
		this.recommendationsRetriever = new RecommendationsRetriever();		
		this.estimatedPreferenceCache = new Cache<LongPair, Float>(new EstimatedPreferenceRetriever(), numUsers);
		this.recommendationCache = new Cache<Long, Recommendations>(recommendationsRetriever, numUsers);
	}

	/**
	 * 
	 */
	protected void initializeRefreshHelper() {
		/**
		 * Configuring refresh helper adding all recommenders like dependencies
		 */
		this.refreshHelper = new RefreshHelper(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				clear();
				return null;
			}
		});
		
		if (recommenderMap != null && recommenderMap.values() != null && ! recommenderMap.values().isEmpty()) {			
			for (Recommender recommender : recommenderMap.values()) {
				this.refreshHelper.addDependency(recommender);
			}
		}
	}
	
	/**
	 * 
	 * @param dataModelMap
	 * @param recommenderBuilder
	 * @throws TasteException
	 */
	protected void initializeRecommenders(Map<Long, DataModel> dataModelMap,
			RecommenderBuilder recommenderBuilder)
			throws TasteException {
		this.recommenderMap = new HashMap<>();
		for (Map.Entry<Long,DataModel> entry : dataModelMap.entrySet()) {
			Recommender recommender = recommenderBuilder.buildRecommender(entry.getValue());
			recommenderMap.put(entry.getKey(), recommender);
		}
	}

	/**
	 * 
	 * @param dataModelMap
	 * @param recommenderBuilderMap
	 * @throws TasteException
	 */
	protected void initializeRecommenders(Map<Long, DataModel> dataModelMap,
			Map<Long, RecommenderBuilder> recommenderBuilderMap)
			throws TasteException {
		this.recommenderMap = new HashMap<>();
		for (Map.Entry<Long,DataModel> entry : dataModelMap.entrySet()) {
			RecommenderBuilder recommenderBuilder = recommenderBuilderMap.get(entry.getKey());
			Recommender recommender = recommenderBuilder.buildRecommender(entry.getValue());
			recommenderMap.put(entry.getKey(), recommender);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected static CandidateItemsStrategy getDefaultCandidateItemsStrategy() {
		return new PreferredItemsNeighborhoodCandidateItemsStrategy();
	}
	
	/**
	 * 
	 * @param rescorer
	 */
	private void setCurrentRescorer(IDRescorer rescorer) {
		if (rescorer == null) {
			if (currentRescorer != null) {
				currentRescorer = null;
				clear();
			}
		} else {
			if (!rescorer.equals(currentRescorer)) {
				currentRescorer = rescorer;
				clear();
			}
		}
	}

	/* (non-Javadoc)
	 * @see br.ufba.dcc.mestrado.computacao.recommender.impl.MultiCriteriaRecommender#refresh(java.util.Collection)
	 */
	@Override
	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		this.refreshHelper.refresh(alreadyRefreshed);
	}
		
	/* (non-Javadoc)
	 * @see br.ufba.dcc.mestrado.computacao.recommender.impl.MultiCriteriaRecommender#recommend(long, int)
	 */
	@Override
	public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
		return recommend(userID, howMany, null);
	}

	/* (non-Javadoc)
	 * @see br.ufba.dcc.mestrado.computacao.recommender.impl.MultiCriteriaRecommender#recommend(long, int, org.apache.mahout.cf.taste.recommender.IDRescorer)
	 */
	@Override
	public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
		Preconditions.checkArgument(howMany >= 1, "howMany must be at least 1");
		
		synchronized (maxHowMany) {
			if (howMany > maxHowMany) {
				maxHowMany = howMany;
			}
		}
		
		// Special case, avoid caching an anonymous user
		if (userID == PlusAnonymousUserDataModel.TEMP_USER_ID) {
			return recommendationsRetriever.get(PlusAnonymousUserDataModel.TEMP_USER_ID).getItems();
		}
		
		setCurrentRescorer(rescorer);
		
		//return maxHowMany recommendations to user userID
		Recommendations recommendations = recommendationCache.get(userID);
		
		if (recommendations.getItems().size() < howMany && !recommendations.isNoMoreRecommendableItems()) {
			clear(userID);
			recommendations = recommendationCache.get(userID);
			if (recommendations.getItems().size() < howMany) {
				recommendations.setNoMoreRecommendableItems(true);
			}
		}

		List<RecommendedItem> recommendedItems = recommendations.getItems();
		return recommendedItems.size() > howMany ? recommendedItems.subList(0,howMany) : recommendedItems;
	}
	
	/**
	 * 
	 * @param userID
	 * @param howMany
	 * @param rescorer
	 * @return
	 * @throws TasteException
	 */
	protected List<RecommendedItem> doRecommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
		logger.debug(String.format("Recommending %d items for user '%d'", howMany, userID));
		
		FastIDSet itemsID = getAllOtherItems(userID);
		TopItems.Estimator<Long> estimator = new CachedEstimator(userID);
		
		List<RecommendedItem> topItems = TopItems
		        .getTopItems(howMany, itemsID.iterator(), rescorer, estimator);

		logger.debug(String.format("Recommendations are: %s", topItems.toString()));
	    return topItems;
	}
	
	/**
	 * 
	 * @param userID
	 * @return
	 * @throws TasteException
	 */
	protected FastIDSet getAllOtherItems(long userID) throws TasteException {
		FastIDSet itemIDs = new FastIDSet();
		for (Map.Entry<Long, Recommender> entry : recommenderMap.entrySet()) {
			PreferenceArray preferencesFromUser = entry.getValue().getDataModel().getPreferencesFromUser(userID);
			itemIDs.addAll(candidateItemsStrategy.getCandidateItems(userID, preferencesFromUser, entry.getValue().getDataModel()));
		}
		
		return itemIDs;
	}
	
	/* (non-Javadoc)
	 * @see br.ufba.dcc.mestrado.computacao.recommender.impl.MultiCriteriaRecommender#estimatePreference(long, long)
	 */
	@Override
	public float estimatePreference(long userID, long itemID) throws TasteException {		
		return estimatedPreferenceCache.get(new LongPair(userID, userID));
	}
		
	/**
	 * 
	 * @param userID
	 * @param itemID
	 * @return
	 * @throws TasteException
	 */
	protected float doEstimatePreference (long userID, long itemID) throws TasteException {
		float estimated = 0;
		
		Map<Long,Float> estimatedMap = justifyPreferenceValue(userID, itemID);
		estimated = preferenceAggregatorStrategy.aggregatePreferenceValues(estimatedMap);
		
		return estimated;
	}
	
	/* (non-Javadoc)
	 * @see br.ufba.dcc.mestrado.computacao.recommender.impl.MultiCriteriaRecommender#justifyPreferenceValue(long, long)
	 */
	@Override
	public Map<Long,Float> justifyPreferenceValue(long userID, long itemID) throws TasteException {
		Map<Long,Float> estimatedMap = new HashMap<Long, Float>();
		for (Map.Entry<Long, Recommender> entry : recommenderMap.entrySet()) {
			Recommender recommender = entry.getValue();
			float estimated = recommender.estimatePreference(userID, itemID);
			estimatedMap.put(entry.getKey(), estimated);
		}
		
		return estimatedMap;
	}

	/* (non-Javadoc)
	 * @see br.ufba.dcc.mestrado.computacao.recommender.impl.MultiCriteriaRecommender#setPreference(long, long, float)
	 */
	@Override
	public void setPreference(long userID, long itemID, float value) throws TasteException {
		throw new NotSupportedException();
	}

	/* (non-Javadoc)
	 * @see br.ufba.dcc.mestrado.computacao.recommender.impl.MultiCriteriaRecommender#removePreference(long, long)
	 */
	@Override
	public void removePreference(long userID, long itemID) throws TasteException {
		for (Map.Entry<Long, Recommender> entry : recommenderMap.entrySet()) {
			entry.getValue().removePreference(userID, itemID);
		}
		
		clear(userID);
	}
	
	/**
	 * 
	 * @return
	 * @throws TasteException
	 */
	protected FastIDSet getUserIDs() throws TasteException {
		FastIDSet userIDs = new FastIDSet();
		
		for (Map.Entry<Long, Recommender> entry : recommenderMap.entrySet()) {
			LongPrimitiveIterator iterator = entry.getValue().getDataModel().getUserIDs();
			while (iterator.hasNext()) {
				userIDs.add(iterator.nextLong());
			}
		}
		
		return userIDs;
	}
	
	/**
	 * 
	 * @return
	 * @throws TasteException
	 */
	protected FastIDSet getItemIDs() throws TasteException {
		FastIDSet itemIDs = new FastIDSet();
		
		for (Map.Entry<Long, Recommender> entry : recommenderMap.entrySet()) {
			LongPrimitiveIterator iterator = entry.getValue().getDataModel().getItemIDs();
			while (iterator.hasNext()) {
				itemIDs.add(iterator.nextLong());
			}
		}
		
		return itemIDs;
	}
	

	/* (non-Javadoc)
	 * @see br.ufba.dcc.mestrado.computacao.recommender.impl.MultiCriteriaRecommender#getDataModel()
	 */
	@Override
	public DataModel getDataModel() {
		if (overallDataModel == null) {			
			//throw new NotSupportedException();
			Map<Long, Map<Long, Preference>> userDataMap = new HashMap<>();
			for (Map.Entry<Long, Recommender> entry : recommenderMap.entrySet()) {
				try {
					FastByIDMap<PreferenceArray> userData = GenericDataModel.toDataMap(entry.getValue().getDataModel());
					LongPrimitiveIterator keyIterator = userData.keySetIterator();
					while (keyIterator.hasNext()) {
						Long key = keyIterator.next();
						PreferenceArray preferenceArray = userData.get(key);
						
						Iterator<Preference> preferenceIterator = preferenceArray.iterator();
						while (preferenceIterator.hasNext()) {
							Preference preference = preferenceIterator.next();

							Preference data = null;

							Map<Long, Preference> userPreferencesMap = userDataMap.get(preference.getUserID());

							if (userDataMap.get(preference.getUserID()) != null) {
								data = userPreferencesMap.get(preference.getItemID());
							} else {
								userPreferencesMap = new HashMap<>();
								userDataMap.put(preference.getUserID(), userPreferencesMap);
							}

							if (data == null) {
								float value = estimatePreference(preference.getUserID(),preference.getItemID());
								data = new GenericPreference(preference.getUserID(),preference.getItemID(), value);
								userPreferencesMap.put(preference.getItemID(), data);								
							}
						}
					}
					
				} catch (TasteException ex) {
					
				}
			}
			
			FastByIDMap<PreferenceArray> userData = new FastByIDMap<>(userDataMap.size());
			for (Map.Entry<Long, Map<Long, Preference>> userDataMapEntry : userDataMap.entrySet()) {
				Map<Long, Preference> userPreferencesMap = userDataMapEntry.getValue();
				List<Preference> preferenceList = new ArrayList<>(userPreferencesMap.values());
				
				PreferenceArray preferenceArray = new GenericUserPreferenceArray(preferenceList);
				userData.put(userDataMapEntry.getKey(), preferenceArray);
			}
			
			overallDataModel = new GenericDataModel(userData);
		}
		
		return overallDataModel;
	}
	
	/* (non-Javadoc)
	 * @see br.ufba.dcc.mestrado.computacao.recommender.impl.MultiCriteriaRecommender#clear(long)
	 */
	@Override
	public void clear(final long userID) {
		logger.debug(String.format("Clearing recommendations for user ID '%d'", userID));
		recommendationCache.remove(userID);
		estimatedPreferenceCache
			.removeKeysMatching(new Cache.MatchPredicate<LongPair>() {
				@Override
				public boolean matches(LongPair userItemPair) {
					return userItemPair.getFirst() == userID;
				}
			});
	}
	
	/* (non-Javadoc)
	 * @see br.ufba.dcc.mestrado.computacao.recommender.impl.MultiCriteriaRecommender#clear()
	 */
	@Override
	public void clear() {
		this.estimatedPreferenceCache.clear();
		this.recommendationCache.clear();
	}
	
	/**
	 * 
	 * @author leandro.ferreira
	 *
	 */
	protected class RecommendationsRetriever implements Retriever<Long, Recommendations> {
		
		private Logger logger = Logger.getLogger(RecommendationsRetriever.class.getClass());

		@Override
		public Recommendations get(Long key) throws TasteException {
			logger.debug(String.format("Retrieving recommendations to user %d", key));
			
			int howMany = maxHowMany;
			IDRescorer rescorer = currentRescorer;
			
			List<RecommendedItem> recommendations = (rescorer == null ? doRecommend(key, howMany, null) : doRecommend(key, howMany, rescorer));
			
			return new Recommendations(Collections.unmodifiableList(recommendations));
		}
	}
	
	/**
	 * Utilizado para resgatar o valor de preferência de um usuário para todos os 
	 * critérios
	 */
	protected class EstimatedPreferenceRetriever implements Retriever<LongPair, Float> {
		private Logger logger = Logger.getLogger(EstimatedPreferenceRetriever.class.getClass());

		@Override
		public Float get(LongPair key) throws TasteException {
			long userID = key.getFirst();
			long itemID = key.getSecond();
			
			logger.debug(String.format("Retrieving preference values for user %d and item %d", userID, itemID));
			float estimated = doEstimatePreference(userID, itemID);
			
			return estimated;
		}
	}
	
	/**
	 * @see {@link org.apache.mahout.cf.taste.impl.recommender.CachingRecommender.Recommendations}
	 * @author leandro.ferreira
	 *
	 */
	protected static final class Recommendations {

		private final List<RecommendedItem> items;
		private boolean noMoreRecommendableItems;

		private Recommendations(List<RecommendedItem> items) {
			this.items = items;
		}

		List<RecommendedItem> getItems() {
			return items;
		}

		boolean isNoMoreRecommendableItems() {
			return noMoreRecommendableItems;
		}

		void setNoMoreRecommendableItems(boolean noMoreRecommendableItems) {
			this.noMoreRecommendableItems = noMoreRecommendableItems;
		}
	}
	
	/**
	 * 
	 * @author leandro.ferreira
	 *
	 */
	protected final class CachedEstimator implements TopItems.Estimator<Long> {
		
		private Long userID;
		
		CachedEstimator(Long userID) {
			this.userID = userID;
		}

		@Override
		public double estimate(Long itemID) throws TasteException {			
			return doEstimatePreference(userID, itemID);
		}
		
	}
	
}
