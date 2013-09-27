package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;
import java.util.List;

import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.AllUnknownItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.MostSimilarItemsCandidateItemsStrategy;

import br.ufba.dcc.mestrado.computacao.recommender.CriteriumPreference;

public interface RecommenderService extends Serializable {

	/**
	 * 
	 * @return
	 */
	Long countAll();
	
	/**
	 * 
	 * @return
	 */
	List<CriteriumPreference> findAll();
	
	/**
	 * 
	 * @param limit
	 * @param offset
	 * @return
	 */
	List<CriteriumPreference> findAll(Integer limit, Integer offset);

	/**
	 * 
	 * @param userID
	 * @return
	 */
	Long countAllByUser(Long userID);
	
	/**
	 * 
	 * @param userID
	 * @return
	 */
	List<CriteriumPreference> findAllUser(Long userID);
	
	/**
	 * 
	 * @param userID
	 * @param limit
	 * @param offset
	 * @return
	 */
	List<CriteriumPreference> findAllUser(Long userID, Integer limit, Integer offset);

	/**
	 * 
	 * @param itemID
	 * @return
	 */
	Long countAllByItem(Long itemID);
	
	/**
	 * 
	 * @param itemID
	 * @return
	 */
	List<CriteriumPreference> findAllByItem(Long itemID);
	
	/**
	 * 
	 * @param itemID
	 * @param limit
	 * @param offset
	 * @return
	 */
	List<CriteriumPreference> findAllByItem(Long itemID, Integer limit, Integer offset);
	
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
	List<CriteriumPreference> findAllByCriterium(Long criteriumID);
	
	/**
	 * 
	 * @param criteriumID
	 * @param limit
	 * @param offset
	 * @return
	 */
	List<CriteriumPreference> findAllByCriterium(Long criteriumID, Integer limit, Integer offset);
	
	/**
	 * 
	 * @param criteriumPreferences
	 * @return
	 */
	DataModel buildUserDataModel(List<CriteriumPreference> criteriumPreferences);
	

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
}
