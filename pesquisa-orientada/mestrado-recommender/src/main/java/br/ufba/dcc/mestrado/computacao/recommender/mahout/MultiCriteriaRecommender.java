package br.ufba.dcc.mestrado.computacao.recommender.mahout;

import java.util.Collection;
import java.util.List;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

/**
 * 
 * @author leandro.ferreira
 *
 */
public interface MultiCriteriaRecommender extends Recommender {

	/**
	 * 
	 */
	public void refresh(Collection<Refreshable> alreadyRefreshed);

	/**
	 * 
	 */
	public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException;

	/**
	 * 
	 */
	public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException;

	/**
	 * 
	 */
	public float estimatePreference(long userID, long itemID) throws TasteException;

	/**
	 * 
	 * @param userID
	 * @param itemID
	 * @return
	 * @throws TasteException
	 */
	public FastByIDMap<Float> justifyPreferenceValue(long userID, long itemID) throws TasteException;

	/**
	 * 
	 */
	public void setPreference(long userID, long itemID, float value) throws TasteException;

	/**
	 * 
	 */
	public void removePreference(long userID, long itemID) throws TasteException;

	/**
	 * 
	 */
	public DataModel getDataModel();

	/**
	 * 
	 * @param userID
	 */
	public void clear(long userID);

	/**
	 * 
	 */
	public void clear();
	
	/*
	 * 
	 */
	public PreferenceAggregatorStrategy getPreferenceAggregatorStrategy();

}