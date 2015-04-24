package br.ufba.dcc.mestrado.computacao.recommender.evaluator.base;

import org.apache.mahout.cf.taste.common.TasteException;


public interface OfflineRecommenderEvaluator {
	
	void evaluate() throws TasteException;
	
}
