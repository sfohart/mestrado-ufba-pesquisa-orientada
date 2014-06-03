package br.ufba.dcc.mestrado.computacao.service.core.base;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.preference.PreferenceEntity;
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
	 * @param projectId
	 * @return
	 */
	Long countAllLastByProject(Long projectId);
	
	/**
	 * 
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<ImmutablePair<OhLohProjectEntity, Long>> findRatingCountByProject(
			Integer startAt, 
			Integer offset);

}
