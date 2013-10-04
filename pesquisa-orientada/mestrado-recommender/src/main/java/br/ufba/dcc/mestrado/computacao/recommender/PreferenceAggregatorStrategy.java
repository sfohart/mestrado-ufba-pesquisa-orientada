package br.ufba.dcc.mestrado.computacao.recommender;

import java.io.Serializable;
import java.util.Map;

import br.ufba.dcc.mestrado.computacao.exception.RecommenderException;

/**
 * 
 * @author leandro.ferreira
 *
 */
public interface PreferenceAggregatorStrategy extends Serializable {

	float aggregatePreferenceValues(Map<Long,Float> estimatedMap) throws RecommenderException;
	
}
