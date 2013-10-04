package br.ufba.dcc.mestrado.computacao.recommender.impl;

import java.util.Map;

import br.ufba.dcc.mestrado.computacao.exception.InvalidCriteriumWeighException;
import br.ufba.dcc.mestrado.computacao.exception.RecommenderException;
import br.ufba.dcc.mestrado.computacao.recommender.PreferenceAggregatorStrategy;

/**
 * 
 * Calcula a média ponderada dos valores estimados para as preferências dos ítens
 * recomendados
 * 
 * @author leandro.ferreira
 *
 */
public class WeightedAverageAggregatorStrategy implements PreferenceAggregatorStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5064149387431508318L;
	
	private Map<Long, Float> weightMap;
	
	/**
	 * 
	 * @param weightMap Tabela de pesos a ser utilizada
	 */
	public WeightedAverageAggregatorStrategy(Map<Long, Float> weightMap) {
		this.weightMap = weightMap;
	}

	@Override
	public float aggregatePreferenceValues(Map<Long, Float> estimatedMap) throws RecommenderException {
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
