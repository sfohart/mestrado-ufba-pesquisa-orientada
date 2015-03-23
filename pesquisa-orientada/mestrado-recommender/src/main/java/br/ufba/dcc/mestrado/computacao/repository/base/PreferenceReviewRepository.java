package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.preference.PreferenceReviewEntity;

public interface PreferenceReviewRepository extends BaseRepository<Long, PreferenceReviewEntity> {
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	Long countAllLastReviewsByProject(Long projectId);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	Long countAllLastReviewsByUser(Long userId);
	
	/**
	 * 	
	 * @param projectId
	 * @return
	 */
	List<PreferenceReviewEntity> findAllLastReviewsByProject(Long projectId);
	
	/**
	 * 
	 * @param projectId
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<PreferenceReviewEntity> findAllLastReviewsByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset);
	
	/**
	 * 
	 * @param projectId
	 * @param startAt
	 * @param offset
	 * @param orderByRegisteredAt
	 * @param orderByReviewRanking
	 * @return
	 */
	List<PreferenceReviewEntity> findAllLastReviewsByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking);
	
	/**
	 * 
	 * @param userId
	 * @param startAt
	 * @param offset
	 * @param orderByRegisteredAt
	 * @param orderByReviewRanking
	 * @return
	 */
	List<PreferenceReviewEntity> findAllLastReviewsByUser(
			Long userId, 
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking);

}
