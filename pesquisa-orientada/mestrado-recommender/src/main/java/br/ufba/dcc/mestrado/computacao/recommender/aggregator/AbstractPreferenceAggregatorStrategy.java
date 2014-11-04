package br.ufba.dcc.mestrado.computacao.recommender.aggregator;

import java.util.Map;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.model.Preference;

import br.ufba.dcc.mestrado.computacao.exception.RecommenderException;
import br.ufba.dcc.mestrado.computacao.recommender.PreferenceAggregatorStrategy;

public abstract class AbstractPreferenceAggregatorStrategy implements PreferenceAggregatorStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1563633487571324512L;
	
	protected abstract void reset();

	@Override
	public Preference aggregatePreferences(FastByIDMap<Preference> estimatedPreferences) throws RecommenderException {
		
		FastByIDMap<Float> estimatedValues = new FastByIDMap<>();
		Long userID = Long.MIN_VALUE;
		Long itemID = Long.MIN_VALUE;
		
		for (Map.Entry<Long, Preference> entry : estimatedPreferences.entrySet()) {
			estimatedValues.put(entry.getKey(), entry.getValue().getValue());
			
			if (userID == Long.MIN_VALUE) {
				userID = entry.getValue().getUserID();
			}
			
			if (itemID == Long.MIN_VALUE) {
				itemID = entry.getValue().getItemID();
			}
			
		}
		
		float aggregated = aggregatePreferenceValues(estimatedValues);
		
		return new GenericPreference(userID, itemID, aggregated);
	}
	
	
}
