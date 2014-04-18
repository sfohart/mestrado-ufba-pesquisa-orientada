package br.ufba.dcc.mestrado.computacao.recommender.mahout.eval;

import org.apache.mahout.cf.taste.impl.common.FullRunningAverage;
import org.apache.mahout.cf.taste.impl.common.RunningAverage;
import org.apache.mahout.cf.taste.model.Preference;

import br.ufba.dcc.mestrado.computacao.recommender.mahout.PreferenceAggregatorStrategy;

public class RMSMultiCriteriaRecommenderEvaluator extends
		AbstractDifferenceMultiCriteriaRecommenderEvaluator {

	private RunningAverage average;
	
	public RMSMultiCriteriaRecommenderEvaluator(PreferenceAggregatorStrategy preferenceAggregatorStrategy) {
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
		double diff = realPref.getValue() - estimatedPreference;
	    this.average.addDatum(diff * diff);
	}

	@Override
	protected double computeFinalEvaluation() {
		return Math.sqrt(average.getAverage());
	}

}
