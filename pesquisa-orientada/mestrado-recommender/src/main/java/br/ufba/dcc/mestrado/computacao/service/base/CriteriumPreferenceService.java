package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.model.Preference;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;

public interface CriteriumPreferenceService extends BaseOhLohService<Long, PreferenceEntity> , Serializable {
	
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
	 */
	Map<RecommenderCriteriumEntity, Double> averagePreferenceByProject(Long projectId);
	
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
