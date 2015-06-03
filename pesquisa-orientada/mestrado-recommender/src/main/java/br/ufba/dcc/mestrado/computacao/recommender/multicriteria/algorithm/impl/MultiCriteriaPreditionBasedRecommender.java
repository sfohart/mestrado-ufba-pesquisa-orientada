package br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.aggregatefunction.base.AggregatePreferenceStrategy;
import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.base.MultiCriteriaRecommender;

public class MultiCriteriaPreditionBasedRecommender
		extends AbstractMultiCriteriaRecommender
		implements MultiCriteriaRecommender {

	private AggregatePreferenceStrategy aggregatePreference;
	private GenericDataModel dataModel;
	private Recommender overallRecommender;
	
	
	public MultiCriteriaPreditionBasedRecommender(
			RecommenderBuilder recommenderBuilder,
			AggregatePreferenceStrategy aggregatePreference,
			Map<RecommenderCriteriumEntity, DataModel> dataModelMap) throws TasteException {
		
		super(recommenderBuilder, dataModelMap);		
		this.aggregatePreference = aggregatePreference;
		
		buildOverallDataModel();
		initializeOverallRecommender();		
		
	}
	
	public MultiCriteriaPreditionBasedRecommender(
			RecommenderBuilder recommenderBuilder,
			AggregatePreferenceStrategy aggregatePreference,
			Map<RecommenderCriteriumEntity, DataModel> dataModelMap,
			CandidateItemsStrategy candidateItemsStrategy) throws TasteException {
		super(recommenderBuilder, dataModelMap, candidateItemsStrategy);
		this.aggregatePreference = aggregatePreference;
		
		buildOverallDataModel();
		initializeOverallRecommender();
	}

	private void initializeOverallRecommender() {
		if (dataModel != null) {
			try {
				overallRecommender = recommenderBuilder.buildRecommender(getDataModel());
			} catch (TasteException e) {
				e.printStackTrace();
			}
		}
	}

	private void buildOverallDataModel() throws TasteException {
		
		Map<ImmutablePair<Long,Long>, Map<RecommenderCriteriumEntity, Preference>> data = new HashMap<ImmutablePair<Long,Long>, Map<RecommenderCriteriumEntity,Preference>>();
		
		if (dataModelMap != null) {
			for (Map.Entry<RecommenderCriteriumEntity, DataModel> entry : dataModelMap.entrySet()) {
				DataModel dataModel = entry.getValue();
				
				Recommender criteriumRecommender = recommenderBuilder.buildRecommender(dataModel);
				
				try {
					LongPrimitiveIterator userIterator = dataModel.getUserIDs();
					while (userIterator.hasNext()) {
						long userID = userIterator.nextLong();
						
						PreferenceArray preferenceArray = dataModel.getPreferencesFromUser(userID);
						Iterator<Preference> iterator = preferenceArray.iterator();
						
						while (iterator.hasNext()) {
							Preference preference = iterator.next();
							
							if (Float.valueOf(0.0f).equals(preference.getValue()) || preference.getValue() == Float.NaN) {
								float value = criteriumRecommender.estimatePreference(
										preference.getUserID(), 
										preference.getItemID());
								preference.setValue(value);
							}
						
							ImmutablePair<Long, Long> keyPair = new ImmutablePair<Long, Long>(
									preference.getUserID(), 
									preference.getItemID());
							
							Map<RecommenderCriteriumEntity, Preference> preferenceMap = data.get(keyPair);
							if (preferenceMap == null) {
								preferenceMap = new HashMap<RecommenderCriteriumEntity, Preference>();
							}
							
							preferenceMap.put(entry.getKey(), preference);
														
							data.put(keyPair, preferenceMap);
						}
					}
				} catch (TasteException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		Map<Long, List<Preference>> preferenceMap = new HashMap<Long, List<Preference>>();
		
		
		for (Map.Entry<ImmutablePair<Long,Long>, Map<RecommenderCriteriumEntity, Preference>> entry : data.entrySet()) {
			float value = aggregatePreference.aggregatePreference(entry.getValue());
			
			List<Preference> preferenceList = preferenceMap.get(entry.getKey().getLeft());
			if (preferenceList == null) {
				preferenceList = new ArrayList<Preference>();
			}
			
			GenericPreference preference = new GenericPreference(
					entry.getKey().getLeft(), 
					entry.getKey().getRight(),
					value);
			
			preferenceList.add(preference);
			
			preferenceMap.put(entry.getKey().getLeft(), preferenceList);
		}
		
		FastByIDMap<Collection<Preference>> userData = new FastByIDMap<Collection<Preference>>(preferenceMap.size());
		for( Map.Entry<Long, List<Preference>> entry : preferenceMap.entrySet()) {
			userData.put(entry.getKey(), entry.getValue());
		}
		
		dataModel = new GenericDataModel(GenericDataModel.toDataMap(userData, true));
		
	}

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany,
			IDRescorer rescorer, boolean includeKnownItems)
			throws TasteException {
		return overallRecommender.recommend(userID, howMany, rescorer, includeKnownItems);
	}

	@Override
	public float estimatePreference(long userID, long itemID)
			throws TasteException {		
		return overallRecommender.estimatePreference(userID, itemID);
	}

	@Override
	public DataModel getDataModel() {
		return dataModel;
	}

	@Override
	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		
	}
	
	

}
