package br.ufba.dcc.mestrado.computacao.recommender;

import java.io.Serializable;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.model.Preference;

import br.ufba.dcc.mestrado.computacao.exception.RecommenderException;

/**
 * 
 * @author leandro.ferreira
 *
 */
public interface PreferenceAggregatorStrategy extends Serializable {

	float aggregatePreferenceValues(FastByIDMap<Float> estimatedValues) throws RecommenderException;
	
	Preference aggregatePreferences(FastByIDMap<Preference> estimatedMap) throws RecommenderException;
	
}
