package br.ufba.dcc.mestrado.computacao.repository.base;

import java.io.Serializable;
import java.util.List;

import org.apache.mahout.cf.taste.model.Preference;

import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;

public interface CriteriumPreferenceRepository extends BaseRepository<Long, PreferenceEntity>, Serializable {
	
	/**
	 * 
	 * @param criteriumID
	 * @return
	 */
	Long countAllByCriterium(Long criteriumID);
	
	
	/**
	 * 
	 * @param projectId
	 * @param criteriumId
	 * @return
	 */
	Double averagePreferenceByProjectAndCriterium(Long projectId, Long criteriumId);
	
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
