package br.ufba.dcc.mestrado.computacao.recommender.evaluator.impl;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.base.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.MultiCriteriaRecommenderService;
import br.ufba.dcc.mestrado.computacao.service.recommender.impl.MultiCriteriaListBasedRecommenderServiceImpl;

@Component(MultiCriteriaListBasedRecommenderEvaluator.BEAN_NAME)
public class MultiCriteriaListBasedRecommenderEvaluator extends AbstractMultiCriteriaRecommenderEvaluator {
	
	public static final String BEAN_NAME = "multiCriteriaListBasedRecommenderEvaluator";

	@Autowired
	@Qualifier(MultiCriteriaListBasedRecommenderServiceImpl.BEAN_NAME)
	private MultiCriteriaRecommenderService recommenderService;
	
	@Override
	protected MultiCriteriaRecommender buildMultiCriteriaRecommender(RecommenderBuilder recommenderBuilder) throws TasteException {
		return recommenderService.buildMultiCriteriaRecommender(recommenderBuilder);
	}
	
	public static void main(String[] args) throws TasteException {
		OfflineRecommenderEvaluatorUtils.runEvaluator(MultiCriteriaListBasedRecommenderEvaluator.BEAN_NAME);
	}

}
