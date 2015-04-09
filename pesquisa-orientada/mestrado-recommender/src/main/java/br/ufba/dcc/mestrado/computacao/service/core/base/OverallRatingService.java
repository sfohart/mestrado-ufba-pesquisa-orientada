
package br.ufba.dcc.mestrado.computacao.service.core.base;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.service.base.BaseService;

public interface OverallRatingService extends BaseService<Long, PreferenceEntity> {
	
	/**
	 * 
	 * @param itemId
	 * @return
	 */
	Double averageRatingByItem(Long itemId);
	
	/**
	 * 
	 * @param itemId
	 * @return
	 */
	PreferenceEntity averagePreferenceByItem(Long itemId);
	
	/**
	 * 
	 * @return
	 */
	Map<ImmutablePair<Long, Long>, Double> findAllLastOverallPreferenceValue();
	
	/**
	 * 
	 * @param itemId
	 * @return
	 */
	Map<ImmutablePair<Long, Long>, Double> findAllLastOverallPreferenceValueByItem(Long itemId);
	
	/**
	 * 
	 * @param userId
	 * @param itemId
	 * @return
	 */
	PreferenceEntity findLastOverallPreferenceByUserAndItem(Long userId, Long itemId);
	
	
	/**
	 * 
	 * @return
	 */
	Long countAllLast();
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	Long countAllLastPreferenceByProject(Long projectId);
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	Long countAllLastPreferenceByUser(Long userId);
	
	/**
	 * 
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<ImmutablePair<OpenHubProjectEntity, Long>> findRatingCountByProject(
			Integer startAt, 
			Integer offset);

	/**
	 * 
	 * @param userId
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<PreferenceEntity> findAllLastPreferenceByUser(
			Long userId,
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking);

	/**
	 * 
	 * @param projectId
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<PreferenceEntity> findAllLastPreferenceByProject(
			Long projectId,
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking);

}

