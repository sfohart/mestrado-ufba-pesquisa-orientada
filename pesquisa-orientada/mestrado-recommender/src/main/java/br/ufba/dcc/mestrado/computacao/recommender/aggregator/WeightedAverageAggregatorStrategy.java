package br.ufba.dcc.mestrado.computacao.recommender.aggregator;

import java.util.Map;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.WeightedRunningAverage;

import br.ufba.dcc.mestrado.computacao.exception.RecommenderException;

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
	private WeightedRunningAverage average;
	
	/**
	 * 
	 * @param weightMap Tabela de pesos a ser utilizada
	 */
	public WeightedAverageAggregatorStrategy(FastByIDMap<Float> weightMap) {
		this.weightMap = weightMap;
		reset();
	}
	
	protected void reset() {
		this.average = new WeightedRunningAverage();
	}

	@Override
	public float aggregatePreferenceValues(FastByIDMap<Float> estimatedMap) throws RecommenderException {
		reset();
		
		for (Map.Entry<Long, Float> entry : estimatedMap.entrySet()) {
			float weight = weightMap.get(entry.getKey());
			float datum = entry.getValue();
			
			average.addDatum(datum, weight);
		}

		return (float) average.getAverage();
	}

	

}
