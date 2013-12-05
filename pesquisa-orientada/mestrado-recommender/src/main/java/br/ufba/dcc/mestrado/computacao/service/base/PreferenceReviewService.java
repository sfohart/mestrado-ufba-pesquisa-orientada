package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceReviewEntity;

/**
 * 
 * @author leandro.ferreira
 *
 */
public interface PreferenceReviewService extends BaseOhLohService<Long, PreferenceReviewEntity>, Serializable {
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	Long countAllLastReviewsByProject(OhLohProjectEntity project);
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	List<PreferenceReviewEntity> findAllLastReviewsByProject(OhLohProjectEntity project);
	
	/**
	 * 
	 * @param project
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<PreferenceReviewEntity> findAllLastReviewsByProject(OhLohProjectEntity project, Integer startAt, Integer offset);
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	PreferenceReviewEntity findMostHelpfulFavorableReview(OhLohProjectEntity project);
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	PreferenceReviewEntity findMostHelpfulCriticalReview(OhLohProjectEntity project);

}
