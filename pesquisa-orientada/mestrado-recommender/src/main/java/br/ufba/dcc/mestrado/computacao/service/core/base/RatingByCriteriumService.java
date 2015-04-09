package br.ufba.dcc.mestrado.computacao.service.core.base;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

public interface RatingByCriteriumService extends Serializable {
	
Double averagePreferenceByItemAndCriterium(Long itemId, Long criteriumId);
	
	/**
	 * 
	 * @param criteriumId
	 * @return
	 */
	Map<ImmutablePair<Long, Long>, Double> findAllLastPreferenceByCriterium(Long criteriumId);
	
	/**
	 * 
	 * @param criteriumId
	 * @param itemId
	 * @return
	 */
	Map<ImmutablePair<Long, Long>, Double> findAllLastPreferenceByCriteriumAndItem(Long criteriumId, Long itemId);
	
	/**
	 * 
	 * @param criteriumID
	 * @return
	 */
	Long countAllLastByCriterium(Long criteriumId);


}
