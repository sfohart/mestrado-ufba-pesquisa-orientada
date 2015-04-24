package br.ufba.dcc.mestrado.computacao.recommender.evaluator.impl;

import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.base.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.service.mahout.base.MultiCriteriaRecommenderService;
import br.ufba.dcc.mestrado.computacao.service.mahout.impl.MultiCriteriaPredictionBasedRecommenderServiceImpl;

@Component
public class MultiCriteriaPredictionBasedRecommenderEvaluator extends AbstractMultiCriteriaRecommenderEvaluator {

	@Autowired
	@Qualifier(MultiCriteriaPredictionBasedRecommenderServiceImpl.BEAN_NAME)
	private MultiCriteriaRecommenderService recommenderService;
	
	@Override
	protected MultiCriteriaRecommender buildMultiCriteriaRecommender(RecommenderBuilder recommenderBuilder) {
		return recommenderService.buildMultiCriteriaRecommender(recommenderBuilder);
	}

}
