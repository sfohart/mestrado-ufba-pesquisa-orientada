package br.ufba.dcc.mestrado.computacao.recommender.evaluator.impl;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.ufba.dcc.mestrado.computacao.recommender.evaluator.base.OfflineRecommenderEvaluator;

public class OfflineRecommenderEvaluatorUtils  {

	private static final Logger logger = Logger.getLogger(OfflineRecommenderEvaluatorUtils.class.getName());
	
	public static void runEvaluator(String beanName) throws TasteException {
		logger.info(String.format("Obtendo avaliador %s", beanName));
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("br.ufba.dcc.mestrado.computacao"); // Use annotated beans from the specified package
		
		OfflineRecommenderEvaluator evaluator = (OfflineRecommenderEvaluator) context.getBean(beanName);
		
		logger.info(String.format("Executando avaliador %s", beanName));
		
		evaluator.evaluate();
		context.close();
	}
	
}
