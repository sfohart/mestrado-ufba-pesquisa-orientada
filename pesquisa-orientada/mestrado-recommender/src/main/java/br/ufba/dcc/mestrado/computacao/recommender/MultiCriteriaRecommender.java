package br.ufba.dcc.mestrado.computacao.recommender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
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

import com.google.common.base.Preconditions;

/**
 * 
 * @author leandro.ferreira
 *
 */
public class MultiCriteriaRecommender implements Recommender {
	
	private Logger logger = Logger.getLogger(MultiCriteriaRecommender.class.getName());
	
	private Map<Long,Recommender> recommenderMap;
	private PreferenceAggregatorStrategy preferenceAggregatorStrategy;
	
	private final Cache<LongPair,Float> estimatedPreferenceCache;
	private final Cache<Long,Recommendations> recommendationCache;
	
	private Integer maxHowMany;
	private IDRescorer currentRescorer;
	
	private final Retriever<Long, Recommendations> recommendationsRetriever;
	private final RefreshHelper refreshHelper;
	
	private DataModel overallDataModel;
	private CandidateItemsStrategy candidateItemsStrategy;
	
	/**
	 * 
	 * @param recommenderMap
	 */
	public MultiCriteriaRecommender(Map<Long,Recommender> recommenderMap) {
		this   (MultiCriteriaRecommender.getDefaultCandidateItemsStrategy(),
				recommenderMap,
				new AveragePreferenceAggregatorStrategy());		
	}
	
	/**
	 * 
	 * @param candidateItemsStrategy
	 * @param recommenderMap
	 */
	public MultiCriteriaRecommender(
			CandidateItemsStrategy candidateItemsStrategy,			
			Map<Long,Recommender> recommenderMap) {
		this   (candidateItemsStrategy,				
				recommenderMap,
				new AveragePreferenceAggregatorStrategy());		
	}
	
	/**
	 * 
	 * @param candidateItemsStrategy
	 * @param recommenderMap
	 * @param preferenceAggregatorStrategy
	 */
	public MultiCriteriaRecommender(
			CandidateItemsStrategy candidateItemsStrategy,
			Map<Long,Recommender> recommenderMap,
			PreferenceAggregatorStrategy preferenceAggregatorStrategy) {
		
		this.candidateItemsStrategy = candidateItemsStrategy;
		
		this.recommenderMap = recommenderMap;		
		this.preferenceAggregatorStrategy = preferenceAggregatorStrategy;
		
		this.maxHowMany = 1;
		
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
	
	protected static CandidateItemsStrategy getDefaultCandidateItemsStrategy() {
		return new PreferredItemsNeighborhoodCandidateItemsStrategy();
	}
	
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

	@Override
	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		this.refreshHelper.refresh(alreadyRefreshed);
	}
		
	@Override
	public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
		return recommend(userID, howMany, null);
	}

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
		estimated = preferenceAggregatorStrategy.aggregatePreferenceValues(estimatedMap, userID);
		
		return estimated;
	}
	
	/**
	 * 
	 * @param userID
	 * @param itemID
	 * @return
	 * @throws TasteException
	 */
	public Map<Long,Float> justifyPreferenceValue(long userID, long itemID) throws TasteException {
		Map<Long,Float> estimatedMap = new HashMap<Long, Float>();
		for (Map.Entry<Long, Recommender> entry : recommenderMap.entrySet()) {
			Recommender recommender = entry.getValue();
			float estimated = recommender.estimatePreference(userID, itemID);
			estimatedMap.put(entry.getKey(), estimated);
		}
		
		return estimatedMap;
	}

	@Override
	public void setPreference(long userID, long itemID, float value) throws TasteException {
		throw new NotSupportedException();
	}

	@Override
	public void removePreference(long userID, long itemID) throws TasteException {
		for (Map.Entry<Long, Recommender> entry : recommenderMap.entrySet()) {
			entry.getValue().removePreference(userID, itemID);
		}
		
		clear(userID);
	}
	
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
	

	@Override
	public DataModel getDataModel() {
		if (overallDataModel == null) {
			throw new NotSupportedException();
		}
		
		return overallDataModel;
	}
	
	/**
	 * 
	 * @param userID
	 */
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
	
	/**
	 * 
	 */
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
