package br.ufba.dcc.mestrado.computacao.recommender.multicriteria.aggregatefunction.base;

import java.util.Map;

import org.apache.mahout.cf.taste.model.Preference;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;

public interface AggregatePreferenceStrategy {
	
	float aggregatePreference(Map<RecommenderCriteriumEntity, Preference> preferenceMap);
	
}
