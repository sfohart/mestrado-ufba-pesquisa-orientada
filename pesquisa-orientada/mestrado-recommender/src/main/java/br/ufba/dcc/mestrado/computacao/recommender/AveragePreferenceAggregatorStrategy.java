package br.ufba.dcc.mestrado.computacao.recommender;

import java.util.Map;

import br.ufba.dcc.mestrado.computacao.exception.RecommenderException;

/**
 * 
 * @author leandro.ferreira
 *
 */
public class AveragePreferenceAggregatorStrategy implements PreferenceAggregatorStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9203302821166748172L;

	@Override
	public float aggregatePreferenceValues(Map<Long, Float> estimatedMap, Long userID) throws RecommenderException {
		float estimated = 0;
		
		for (Float item : estimatedMap.values()) {
			estimated += item;
		}
		
		estimated /= estimatedMap.size();
		
		return estimated;
	}

}
