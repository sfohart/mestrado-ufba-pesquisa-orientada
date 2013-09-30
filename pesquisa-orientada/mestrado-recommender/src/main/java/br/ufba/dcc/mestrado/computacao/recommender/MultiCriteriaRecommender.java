package br.ufba.dcc.mestrado.computacao.recommender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import br.ufba.dcc.mestrado.computacao.exception.NotSupportedException;

public class MultiCriteriaRecommender implements Recommender {
	
	private Map<Long,Recommender> recommenderMap;
	private PreferenceAggregatorStrategy preferenceAggregatorStrategy;
	private FastIDSet userIDs;
	
	public MultiCriteriaRecommender(
			Map<Long,Recommender> recommenderMap) {
		this.recommenderMap = recommenderMap;
		this.preferenceAggregatorStrategy = new AveragePreferenceAggregatorStrategy();
	}
	
	public MultiCriteriaRecommender(
			Map<Long,Recommender> recommenderMap,
			PreferenceAggregatorStrategy preferenceAggregatorStrategy) {
		this.recommenderMap = recommenderMap;
		this.preferenceAggregatorStrategy = preferenceAggregatorStrategy;
	}

	@Override
	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		
	}
	
	/**
	 * 
	 * @param userID
	 * @param howMany
	 * @return
	 * @throws TasteException
	 */
	public FastIDSet collectRecommendedItemIDs(long userID, int howMany, IDRescorer rescorer) throws TasteException {
		FastIDSet allItemIDs = new FastIDSet(); 
		
		//faça um apanhado de recomendações de cada critéri
		for (Map.Entry<Long, Recommender> entry : recommenderMap.entrySet()) {
			List<RecommendedItem> recommendedItems = null;
			Recommender recommender = entry.getValue();
			
			try {
				if (rescorer != null) {
					recommendedItems = recommender.recommend(userID, howMany, rescorer);
				} else {
					recommendedItems = recommender.recommend(userID, howMany);
				}				
			
				for (RecommendedItem item : recommendedItems) {
					allItemIDs.add(item.getItemID());
				}
			} catch (NoSuchUserException nsue) {
				
			}
		}
		
		return allItemIDs;
	}
	
	public LongPrimitiveIterator getUserIDs() throws TasteException {
		if (userIDs == null) {
			loadUserIDs();
		}
		
		return userIDs.iterator();
	}
	
	public Integer getNumUsers() throws TasteException {
		if (userIDs == null) {
			loadUserIDs();
		}
		
		return userIDs.size();
	}

	protected void loadUserIDs() throws TasteException {
		userIDs = new FastIDSet();
		
		for (Map.Entry<Long, Recommender> entry : recommenderMap.entrySet()) {
			LongPrimitiveIterator iterator = entry.getValue().getDataModel().getUserIDs();
			while (iterator.hasNext()) {
				userIDs.add(iterator.nextLong());
			}
		}
	}
	
	

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
		return recommend(userID, howMany, null);
	}

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
		FastIDSet allItemIDs = collectRecommendedItemIDs(userID, howMany, rescorer);
		
		List<RecommendedItem> recommendedItemList = new ArrayList<>();
		LongPrimitiveIterator iterator = allItemIDs.iterator();
		
		while (iterator.hasNext()) {
			Long itemID = iterator.next();
			
			Float estimatedValue = estimatePreference(userID, itemID);
			RecommendedItem recommendedItem = new GenericRecommendedItem(itemID, estimatedValue);
			recommendedItemList.add(recommendedItem);
		}
		
		if (! recommendedItemList.isEmpty()) {
			//valores maiores primeiro
			Comparator<RecommendedItem> comparator = new Comparator<RecommendedItem>() {
				@Override
				public int compare(RecommendedItem item1, RecommendedItem item2) {
					int result = 0;
					
					if (item1.getValue() < item2.getValue()) {
						result = 1;
					} else if (item1.getValue() > item2.getValue()) {
						result = -1;
					}
					
					return result;
				}
			};
			
			Collections.sort(recommendedItemList, comparator);
			
			return recommendedItemList.subList(0, howMany);
		} else {
			return recommendedItemList;
		}
	}

	@Override
	public float estimatePreference(long userID, long itemID) throws TasteException {
		float estimated = 0;
		
		Map<Long,Float> estimatedMap = justifyRecommendation(userID, itemID);
		
		estimated = preferenceAggregatorStrategy.aggregatePreferenceValues(estimatedMap);
		
		return estimated;
	}
	
	public Map<Long,Float> justifyRecommendation(long userID, long itemID) throws TasteException {
		Map<Long,Float> estimatedMap = new HashMap<Long, Float>();
		for (Map.Entry<Long, Recommender> entry : recommenderMap.entrySet()) {
			estimatedMap.put(entry.getKey(), entry.getValue().estimatePreference(userID, itemID));
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
	}

	@Override
	public DataModel getDataModel() {
		throw new NotSupportedException();
	}
	
}
