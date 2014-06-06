package br.ufba.dcc.mestrado.computacao.recommender.eval;

import org.apache.mahout.cf.taste.impl.common.FullRunningAverage;
import org.apache.mahout.cf.taste.impl.common.RunningAverage;
import org.apache.mahout.cf.taste.model.Preference;

import br.ufba.dcc.mestrado.computacao.recommender.PreferenceAggregatorStrategy;

public class AverageAbsoluteDifferenceMultiCriteriaRecommenderEvaluator extends
		AbstractDifferenceMultiCriteriaRecommenderEvaluator {

	private RunningAverage average;
	
	public AverageAbsoluteDifferenceMultiCriteriaRecommenderEvaluator(PreferenceAggregatorStrategy preferenceAggregatorStrategy) {
		super(preferenceAggregatorStrategy);
		this.average = new FullRunningAverage(); 
	}
	
	@Override
	protected void reset() {
		this.average = new FullRunningAverage();
	}

	@Override
	protected void processOneEstimate(float estimatedPreference,
			Preference realPref) {
		this.average.addDatum(Math.abs(realPref.getValue() - estimatedPreference));
	}

	@Override
	protected double computeFinalEvaluation() {
		return this.average.getAverage();
	}

}
