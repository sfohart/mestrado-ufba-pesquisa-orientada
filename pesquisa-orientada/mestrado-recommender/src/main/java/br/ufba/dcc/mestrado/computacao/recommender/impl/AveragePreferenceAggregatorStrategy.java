package br.ufba.dcc.mestrado.computacao.recommender.impl;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;

import br.ufba.dcc.mestrado.computacao.exception.RecommenderException;
import br.ufba.dcc.mestrado.computacao.recommender.aggregator.AbstractPreferenceAggregatorStrategy;

/**
 * 
 * @author leandro.ferreira
 *
 */
public class AveragePreferenceAggregatorStrategy extends AbstractPreferenceAggregatorStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9203302821166748172L;

	@Override
	public float aggregatePreferenceValues(FastByIDMap<Float> estimatedMap) throws RecommenderException {
		float estimated = 0;
		
		for (Float item : estimatedMap.values()) {
			estimated += item;
		}
		
		estimated /= estimatedMap.size();
		
		return estimated;
	}

}
