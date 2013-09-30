package br.ufba.dcc.mestrado.computacao.recommender;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author leandro.ferreira
 *
 */
public interface PreferenceAggregatorStrategy extends Serializable {

	float aggregatePreferenceValues(Map<Long,Float> estimatedMap);
	
}
