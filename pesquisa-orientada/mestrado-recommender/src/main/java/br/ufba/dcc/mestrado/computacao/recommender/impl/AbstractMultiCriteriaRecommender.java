package br.ufba.dcc.mestrado.computacao.recommender.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.recommender.base.MultiCriteriaRecommender;

public abstract class AbstractMultiCriteriaRecommender implements
		MultiCriteriaRecommender {

	protected Map<RecommenderCriteriumEntity, DataModel> dataModelMap;
	protected List<RecommenderCriteriumEntity> criteriaList;
	protected CandidateItemsStrategy candidateItemsStrategy;
	protected RecommenderBuilder recommenderBuilder;

	protected AbstractMultiCriteriaRecommender(
			RecommenderBuilder recommenderBuilder,
			Map<RecommenderCriteriumEntity, DataModel> dataModelMap) {
		
		this.recommenderBuilder = recommenderBuilder;
		this.dataModelMap = dataModelMap;
		this.criteriaList = new ArrayList<RecommenderCriteriumEntity>();

		if (dataModelMap != null) {
			criteriaList.addAll(dataModelMap.keySet());
		}
		
		
	}

	protected AbstractMultiCriteriaRecommender(
			RecommenderBuilder recommenderBuilder,
			Map<RecommenderCriteriumEntity, DataModel> dataModelMap,
			CandidateItemsStrategy candidateItemsStrategy) {
		this(recommenderBuilder, dataModelMap);
		this.candidateItemsStrategy = candidateItemsStrategy;
	}
	
	/**
	 * The application defines the value zero to not available preferences, when user does not want
	 * rating a criterium. This method removes these values from DataModel's
	 * 
	 * 
	 * @throws TasteException
	 */
	protected void removeNotAvailablePreferences() throws TasteException {
		if (dataModelMap != null) {
			for (DataModel dataModel : dataModelMap.values()) {
				LongPrimitiveIterator itemIterator = dataModel.getItemIDs();
				LongPrimitiveIterator userIterator = dataModel.getUserIDs();
				
				while (userIterator.hasNext()) {
					long userID = userIterator.nextLong();
					while (itemIterator.hasNext()) {
						long itemID = itemIterator.nextLong();
						
						float value = dataModel.getPreferenceValue(userID, itemID);
						if (value <= 0) {
							dataModel.removePreference(userID, itemID);
						}						
					}
				}				
			}
		}
	}

	/**
	 * <p>
	 * Default implementation which just calls
	 * {@link Recommender#recommend(long, int, org.apache.mahout.cf.taste.recommender.IDRescorer)}
	 * , with a {@link org.apache.mahout.cf.taste.recommender.Rescorer} that
	 * does nothing.
	 * </p>
	 */
	@Override
	public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
		return recommend(userID, howMany, null, false);
	}

	/**
	 * <p>
	 * Default implementation which just calls
	 * {@link Recommender#recommend(long, int, org.apache.mahout.cf.taste.recommender.IDRescorer)}
	 * , with a {@link org.apache.mahout.cf.taste.recommender.Rescorer} that
	 * does nothing.
	 * </p>
	 */
	@Override
	public List<RecommendedItem> recommend(long userID, int howMany, boolean includeKnownItems) throws TasteException {
		return recommend(userID, howMany, null, includeKnownItems);
	}

	/**
	 * <p>
	 * Delegates to
	 * {@link Recommender#recommend(long, int, IDRescorer, boolean)}
	 */
	@Override
	public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
		return recommend(userID, howMany, rescorer, false);
	}
	
	/**
	   * <p>
	   * Default implementation which just calls {@link DataModel#setPreference(long, long, float)} for all DataModel's
	   * </p>
	   *
	   * @throws IllegalArgumentException
	   *           if userID or itemID is {@code null}, or if value is {@link Double#NaN}
	   */
	@Override
	public void setPreference(long userID, long itemID, float value) throws TasteException {
		if (dataModelMap != null) {
			for (DataModel dataModel : dataModelMap.values()) {
				if (dataModel != null) {
					dataModel.setPreference(userID, itemID, value);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param userID
	 * @param itemID
	 * @param criterium
	 * @param value
	 * @throws TasteException
	 */
	public void setPreference(long userID, long itemID, RecommenderCriteriumEntity criterium, float value) throws TasteException {
		if (dataModelMap != null && criterium != null) {
			DataModel dataModel = dataModelMap.get(criterium);
			if (dataModel != null) {
				dataModel.setPreference(userID, itemID, value);
			}
		}
	}

	/**
	 * <p>
	 * Default implementation which just calls {@link DataModel#removePreference(long, long)} (Object, Object)} for all  DataModel's
	 * </p>
	 *
	 * @throws IllegalArgumentException
	 *             if userID or itemID is {@code null}
	 */
	@Override
	public void removePreference(long userID, long itemID) throws TasteException {
		if (dataModelMap != null) {
			for (DataModel dataModel : dataModelMap.values()) {
				if (dataModel != null) {
					dataModel.removePreference(userID, itemID);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param userID
	 * @param itemID
	 * @param criterium
	 * @throws TasteException
	 */
	public void removePreference(long userID, long itemID, RecommenderCriteriumEntity criterium) throws TasteException {
		if (dataModelMap != null && criterium != null) {
			DataModel dataModel = dataModelMap.get(criterium);
			if (dataModel != null) {
				dataModel.removePreference(userID, itemID);
			}
		}
	}
	
	
	protected FastIDSet getAllOtherItems(long userID, PreferenceArray preferencesFromUser, boolean includeKnownItems) throws TasteException {
		FastIDSet allOtherItems = null;
		
		if (dataModelMap != null) {
			for (Map.Entry<RecommenderCriteriumEntity, DataModel> entry : dataModelMap.entrySet()) {
				FastIDSet otherItems = candidateItemsStrategy.getCandidateItems(userID, preferencesFromUser, entry.getValue(), includeKnownItems);
			
				if (allOtherItems == null) {
					allOtherItems = otherItems;
				} else {
					allOtherItems.retainAll(otherItems);
				}
			}
		}
		
		return allOtherItems;
	}
	
	/**
	 * 
	 * @param userID
	 * @param preferencesFromUser
	 * @param criterium
	 * @param includeKnownItems
	 * @return
	 * @throws TasteException
	 */
	protected FastIDSet getAllOtherItems(
			long userID, 
			PreferenceArray preferencesFromUser, 
			RecommenderCriteriumEntity criterium, 
			boolean includeKnownItems) throws TasteException {
		
		FastIDSet allOtherItems = null;
		
		if (dataModelMap != null) {
			DataModel dataModel = dataModelMap.get(criterium);
			if (dataModel != null) {
				allOtherItems = candidateItemsStrategy.getCandidateItems(userID, preferencesFromUser, dataModel, includeKnownItems);
			}
		}
		
		return allOtherItems;
	}

}
