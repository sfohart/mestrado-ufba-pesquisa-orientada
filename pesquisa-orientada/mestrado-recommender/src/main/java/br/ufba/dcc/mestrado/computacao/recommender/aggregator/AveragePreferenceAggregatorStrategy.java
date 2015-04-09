package br.ufba.dcc.mestrado.computacao.recommender.aggregator;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FullRunningAverage;
import org.apache.mahout.cf.taste.impl.common.RunningAverage;

import br.ufba.dcc.mestrado.computacao.exception.RecommenderException;

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
	
	private RunningAverage average;
	
	public AveragePreferenceAggregatorStrategy() {
		reset();
	}
	
	protected void reset() {
		this.average = new FullRunningAverage();
	}

	@Override
	public float aggregatePreferenceValues(FastByIDMap<Float> estimatedMap) throws RecommenderException {
		reset();
		
		for (Float item : estimatedMap.values()) {
			average.addDatum(item);
		}
		
		return (float) average.getAverage();
	}

}
