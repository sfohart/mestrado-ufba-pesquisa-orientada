package br.ufba.dcc.mestrado.computacao.recommender;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.model.DataModel;

public interface MultiCriteriaRecommenderBuilder {

	MultiCriteriaRecommender buildRecommender(
			FastByIDMap<DataModel> dataModelMap, 
			FastByIDMap<Float> criteriaWeightMap) throws TasteException;
	
}
