package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;

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
	Map<ImmutablePair<Long, Long>, Double> findAllLastOverallPreference();
	
	/**
	 * 
	 * @param itemId
	 * @return
	 */
	Map<ImmutablePair<Long, Long>, Double> findAllLastOverallPreferenceByItem(Long itemId);
	
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
	Long countAllLastByProject(Long projectId);
	
	/**
	 * 
	 * @return
	 */
	List<ImmutablePair<OhLohProjectEntity, Long>> findRatingCountByProject(
			Integer startAt, 
			Integer offset);
}
