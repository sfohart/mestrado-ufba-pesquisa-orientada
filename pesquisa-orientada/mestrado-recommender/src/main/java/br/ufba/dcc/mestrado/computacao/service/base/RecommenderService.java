package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.BooleanPreference;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.recommender.AllUnknownItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.MostSimilarItemsCandidateItemsStrategy;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaRecommenderBuilder;

public interface RecommenderService extends Serializable {
	
	/**
	 * 
	 * @param criteriumID
	 * @return
	 */
	Long countAllByCriterium(Long criteriumID);
	
	/**
	 * 
	 * @param criteriumID
	 * @return
	 */
	List<Preference> findAllByCriterium(Long criteriumID);
	
	/**
	 * 
	 * @param criteriumID
	 * @param limit
	 * @param offset
	 * @return
	 */
	List<Preference> findAllByCriterium(Long criteriumID, Integer limit, Integer offset);
	
	/**
	 * 
	 * @param Preferences
	 * @return
	 */
	DataModel buildUserDataModel(Long criteriumID);
	
	/**
	 * 
	 * @param Preferences
	 * @return
	 */
	DataModel buildItemDataModel(Long criteriumID);
	

	/**
	 * 
	 * @param dataModel
	 * @return
	 */
	RecommenderBuilder createUserBasedRecomenderBuilder(DataModel dataModel);
	
	
	/**
	 * Utilliza {@link AllUnknownItemsCandidateItemsStrategy} como implementação para
	 * {@link CandidateItemsStrategy} e {@link MostSimilarItemsCandidateItemsStrategy}
	 * @param dataModel
	 * @return
	 */
	RecommenderBuilder createItemBasedRecomenderBuilder(DataModel dataModel);
	
	/**
	 * @param criteriaList
	 * @return
	 */
	FastByIDMap<DataModel> createUserDataModelMap(List<RecommenderCriteriumEntity> criteriaList);
	
	/**
	 * 
	 * @param criteriaList
	 * @return
	 */
	FastByIDMap<DataModel> createItemDataModelMap(List<RecommenderCriteriumEntity> criteriaList);
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws TasteException
	 */
	MultiCriteriaRecommender buildMultiCriteriaRecommender(Long userId) throws TasteException;
	
	/**
	 * 
	 * @return
	 * @throws TasteException
	 */
	MultiCriteriaRecommender buildMultiCriteriaRecommender() throws TasteException;
	
	
	/**
	 * 
	 */
	MultiCriteriaRecommenderBuilder createMultiCriteriaRecommenderBuilder() throws TasteException;

	/**
	 * 
	 */
	List<RecommenderCriteriumEntity> findAllCriteria();

	/**
	 * @param userID
	 */
	FastByIDMap<Float> findUserCriteriaWeight(Long userID);

	DataModel buildItemDataModel(List<Preference> preferenceList);

	DataModel buildUserDataModel(List<Preference> preferenceList);

	RecommenderBuilder createBooleanItemBasedRecomenderBuilder(GenericBooleanPrefDataModel dataModel);

	RecommenderBuilder createBooleanUserBasedRecomenderBuilder(GenericBooleanPrefDataModel dataModel);

	GenericBooleanPrefDataModel buildBooleanDataModel(List<BooleanPreference> preferenceList);

	
	
}
