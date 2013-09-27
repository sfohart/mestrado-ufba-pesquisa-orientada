package br.ufba.dcc.mestrado.computacao.repository.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.recommender.CriteriumPreference;

public interface CriteriumPreferenceRepository extends Serializable {

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
	
}
