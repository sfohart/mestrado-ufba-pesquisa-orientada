package br.ufba.dcc.mestrado.computacao.service.core.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;

public interface BaseColaborativeFilteringService extends Serializable {
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<OhLohProjectEntity> recommendViewedProjectsByUser(Long userId, Integer howManyItems);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<OhLohProjectEntity> recommendViewedProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<OhLohProjectEntity> recommendRatingProjectsByUser(Long userId, Integer howManyItems);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @param filterInterestTags
	 * @return
	 */
	List<OhLohProjectEntity> recommendRatingProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags);
	
	/**
	 * 
	 * @param userId
	 * @param howManyItems
	 * @return
	 */
	List<OhLohProjectEntity> recommendViewedProjectsByItem(Long itemId, Integer howManyItems);

}
