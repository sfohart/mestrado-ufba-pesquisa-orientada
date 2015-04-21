package br.ufba.dcc.mestrado.computacao.recommender.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.recommender.base.MultiCriteriaRecommender;

public class MultiCriteriaListBasedRecommender extends
		AbstractMultiCriteriaRecommender 
		implements MultiCriteriaRecommender {
	
	private Map<RecommenderCriteriumEntity, Recommender> recommenderMap;

	public MultiCriteriaListBasedRecommender(
			RecommenderBuilder recommenderBuilder,
			Map<RecommenderCriteriumEntity, DataModel> dataModelMap) {
		
		super(recommenderBuilder, dataModelMap);	
		
		initializeRecommenderMap();
	}
	
	public MultiCriteriaListBasedRecommender(
			RecommenderBuilder recommenderBuilder,
			Map<RecommenderCriteriumEntity, DataModel> dataModelMap,
			CandidateItemsStrategy candidateItemsStrategy) {
		
		super(recommenderBuilder, dataModelMap, candidateItemsStrategy);
		
		initializeRecommenderMap();
	}

	private void initializeRecommenderMap() {
		recommenderMap = new HashMap<RecommenderCriteriumEntity, Recommender>();
		
		if (criteriaList != null) {
			for (RecommenderCriteriumEntity criterium : criteriaList) {
				try {
					DataModel dataModel = dataModelMap.get(criterium);
					Recommender recommender = recommenderBuilder.buildRecommender(dataModel);
					recommenderMap.put(criterium, recommender);
				} catch (TasteException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer, boolean includeKnownItems) throws TasteException {
		
		Map<Long, Pair<Long, Float>> recommendedItemOccurrence = new HashMap<Long, Pair<Long,Float>>();
		if (criteriaList != null && recommenderMap != null) {
			for (RecommenderCriteriumEntity criterium : criteriaList) {
				Recommender recommender = recommenderMap.get(criterium);
				if (recommender != null) {
					List<RecommendedItem> recommendedItems = recommender.recommend(userID, howMany, rescorer, includeKnownItems);
					
					if (recommendedItems !=  null) {
						for (RecommendedItem recommendedItem : recommendedItems) {
							Pair<Long, Float> pair = recommendedItemOccurrence.get(recommendedItem.getItemID());
							if (pair == null) {
								pair = new ImmutablePair<Long, Float>(1L, recommendedItem.getValue());
							} else {
								pair = new ImmutablePair<Long, Float>(
										pair.getLeft() + 1, 
										pair.getRight() + recommendedItem.getValue());
							}
							
							recommendedItemOccurrence.put(recommendedItem.getItemID(), pair);
							
						}
					}
				}
			}
		}
		

		PriorityQueue<ImmutablePair<RecommendedItem,Long>> queue = 
				new PriorityQueue<ImmutablePair<RecommendedItem,Long>>(new Comparator<ImmutablePair<RecommendedItem,Long>>() {
					
					@Override
					public int compare(ImmutablePair<RecommendedItem, Long> o1, ImmutablePair<RecommendedItem, Long> o2) {
						int result = o1.getRight().compareTo(o2.getRight());
						
						if (result == 0) {
							Float v1 = o1.getLeft().getValue();
							Float v2 = o2.getLeft().getValue();
							
							result = v1.compareTo(v2);
						}
						
						return result;
					}
					
				});
		
		
		for (Map.Entry<Long, Pair<Long, Float>> entry : recommendedItemOccurrence.entrySet()) {
			RecommendedItem recommendedItem = new GenericRecommendedItem(
					entry.getKey(), 
					entry.getValue().getRight());
			
			ImmutablePair<RecommendedItem, Long> pair = new ImmutablePair<RecommendedItem, Long>(
					recommendedItem, 
					entry.getValue().getLeft());
			queue.add(pair);
		}
		
		
		List<RecommendedItem> recommendedItems = new ArrayList<RecommendedItem>();
		for (ImmutablePair<RecommendedItem, Long> pair : queue) {
			recommendedItems.add(pair.getLeft());
		}
		
		return recommendedItems;
	}

	@Override
	public float estimatePreference(long userID, long itemID)
			throws TasteException {
		throw new NotImplementedException("This recommender does not implement this method");
	}

	@Override
	public DataModel getDataModel() {
		throw new NotImplementedException("This recommender does not implement this method");
	}

	@Override
	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		
	}
	
}
