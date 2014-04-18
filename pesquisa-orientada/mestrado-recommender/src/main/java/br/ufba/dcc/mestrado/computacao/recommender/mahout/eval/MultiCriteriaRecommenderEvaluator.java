package br.ufba.dcc.mestrado.computacao.recommender.mahout.eval;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.model.DataModel;

import br.ufba.dcc.mestrado.computacao.recommender.mahout.MultiCriteriaDataModelBuilder;
import br.ufba.dcc.mestrado.computacao.recommender.mahout.MultiCriteriaRecommenderBuilder;

public interface MultiCriteriaRecommenderEvaluator {

	
	double evaluate(MultiCriteriaRecommenderBuilder multiCriteriaRecommenderBuilder,
            MultiCriteriaDataModelBuilder multiCriteriaDataModelBuilder,
            FastByIDMap<DataModel> dataModelMap,
            FastByIDMap<Float> criteriaWeightMap,
            double trainingPercentage,
            double evaluationPercentage) throws TasteException;
	
}
