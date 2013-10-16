package br.ufba.dcc.mestrado.computacao.recommender.impl;

import java.util.Map;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;

import br.ufba.dcc.mestrado.computacao.exception.InvalidCriteriumWeighException;
import br.ufba.dcc.mestrado.computacao.exception.RecommenderException;
import br.ufba.dcc.mestrado.computacao.recommender.aggregator.AbstractPreferenceAggregatorStrategy;

/**
 * 
 * Calcula a média ponderada dos valores estimados para as preferências dos ítens
 * recomendados
 * 
 * @author leandro.ferreira
 *
 */
public class WeightedAverageAggregatorStrategy extends AbstractPreferenceAggregatorStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5064149387431508318L;
	
	private FastByIDMap<Float> weightMap;
	
	/**
	 * 
	 * @param weightMap Tabela de pesos a ser utilizada
	 */
	public WeightedAverageAggregatorStrategy(FastByIDMap<Float> weightMap) {
		this.weightMap = weightMap;
	}

	@Override
	public float aggregatePreferenceValues(FastByIDMap<Float> estimatedMap) throws RecommenderException {
		float aggregated = 0;
		float denominator = 0;
		
		for (Map.Entry<Long, Float> entry : estimatedMap.entrySet()) {
			float weight = weightMap.get(entry.getKey());
			
			denominator += weight;
			aggregated += weight * entry.getValue();
		}
		
		if (denominator <= 0) {
			throw new InvalidCriteriumWeighException();
		} else {
			aggregated /= denominator;
		}
		
		return aggregated;
	}

	

}
