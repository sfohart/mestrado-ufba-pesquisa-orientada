package br.ufba.dcc.mestrado.computacao.service.recommender.base;

import org.apache.mahout.cf.taste.eval.RecommenderBuilder;

import br.ufba.dcc.mestrado.computacao.service.recommender.base.ColaborativeFilteringService;

public interface MahoutColaborativeFilteringService extends ColaborativeFilteringService {

	RecommenderBuilder getUserBasedRecommenderBuilder();

	RecommenderBuilder getBooleanItemBasedRecommenderBuilder();

	RecommenderBuilder getBooleanUserBasedRecommenderBuilder();

}
