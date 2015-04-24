package br.ufba.dcc.mestrado.computacao.recommender.multicriteria.aggregatefunction.impl;

import java.util.Map;

import org.apache.mahout.cf.taste.model.Preference;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.aggregatefunction.base.AggregatePreferenceStrategy;

public class SumAggregatePreferenceStrategy implements
		AggregatePreferenceStrategy {

	@Override
	public float aggregatePreference(Map<RecommenderCriteriumEntity, Preference> preferenceMap) {
		float sum = 0;
		
		for (Preference preference : preferenceMap.values()) {
			sum += preference.getValue();
		}
		
		return sum;
	}

}
