package br.ufba.dcc.mestrado.computacao.service.mahout.base;

import org.apache.mahout.cf.taste.eval.RecommenderBuilder;

import br.ufba.dcc.mestrado.computacao.service.core.base.BaseColaborativeFilteringService;

public interface MahoutColaborativeFilteringService extends BaseColaborativeFilteringService {

	RecommenderBuilder getUserBasedRecommenderBuilder();

	RecommenderBuilder getBooleanItemBasedRecommenderBuilder();

	RecommenderBuilder getBooleanUserBasedRecommenderBuilder();

}
