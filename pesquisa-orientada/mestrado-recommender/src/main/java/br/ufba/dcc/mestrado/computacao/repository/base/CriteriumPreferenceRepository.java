package br.ufba.dcc.mestrado.computacao.repository.base;

import java.io.Serializable;
import java.util.List;

import org.apache.mahout.cf.taste.model.Preference;

public interface CriteriumPreferenceRepository extends Serializable {
	
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
	
}
