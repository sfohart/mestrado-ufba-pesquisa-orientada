package br.ufba.dcc.mestrado.computacao.service.recommender.base;

import org.apache.mahout.cf.taste.eval.RecommenderBuilder;

import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.base.MultiCriteriaRecommender;

public interface MultiCriteriaRecommenderService extends BaseRecommenderService {
	
	MultiCriteriaRecommender buildMultiCriteriaRecommender(RecommenderBuilder recommenderBuilder);
	
}
