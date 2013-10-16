package br.ufba.dcc.mestrado.computacao.recommender.eval;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.model.DataModel;

import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaDataModelBuilder;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaRecommenderBuilder;

public interface MultiCriteriaRecommenderEvaluator {

	
	double evaluate(MultiCriteriaRecommenderBuilder multiCriteriaRecommenderBuilder,
            MultiCriteriaDataModelBuilder multiCriteriaDataModelBuilder,
            FastByIDMap<DataModel> dataModelMap,
            double trainingPercentage,
            double evaluationPercentage) throws TasteException;
	
}
