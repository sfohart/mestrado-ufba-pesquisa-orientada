package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.preference.PreferenceEntity;

public interface OverallRatingRepository extends BaseRepository<Long, PreferenceEntity>{

	/**
	 * 
	 * @param itemId
	 * @return
	 */
	Double averageRatingByItem(Long itemId);
	
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
	 * Conta quantas avaliações existem para um dado projeto. Caso um usuário tenha avaliado o mesmo
	 * projeto mais de uma vez, conta apenas a avaliação mais recente
	 * 
	 * @param projectId
	 * @return
	 */
	Long countAllLastPreferenceByProject(Long projectId);
	
	
	/**
	 * Conta quantas avaliações existem para um dado projeto. Caso um usuário tenha avaliado o mesmo
	 * projeto mais de uma vez, conta apenas a avaliação mais recente
	 * 
	 * @param userId
	 * @return
	 */
	Long countAllLastPreferenceByUser(Long userId);
	
	/**
	 * 
	 * @return
	 */
	List<ImmutablePair<OpenHubProjectEntity, Long>> findRatingCountByProject(
			Integer startAt, 
			Integer offset);

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

	
}
