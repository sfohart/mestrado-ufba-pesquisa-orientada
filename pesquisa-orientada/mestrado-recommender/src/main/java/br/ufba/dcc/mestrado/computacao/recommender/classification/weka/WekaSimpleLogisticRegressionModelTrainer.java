package br.ufba.dcc.mestrado.computacao.recommender.classification.weka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SimpleLogistic;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.spring.RecommenderAppConfig;

@Component
public class WekaSimpleLogisticRegressionModelTrainer extends AbstractWekaModelTrainer {
	
	@Autowired
	public WekaSimpleLogisticRegressionModelTrainer(
			RatingByCriteriumService ratingByCriteriumService,
			RecommenderCriteriumService recommenderCriteriumService,
			OverallRatingService overallRatingService) {
		
		super(ratingByCriteriumService, recommenderCriteriumService, overallRatingService);
		this.setUseNumericToNominalFilter(true);
	}

	@Override
	protected Classifier initializeClassifier() throws Exception {
		Classifier simpleLogistic = new SimpleLogistic();
		
		String[] options = "-I 0 -M 500 -H 50 -W 0.0".split(" ");
		
		simpleLogistic.setOptions(options);
		
		return simpleLogistic;
	}
		
	public static void main(String[] args) throws Exception {		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RecommenderAppConfig.class);

		WekaSimpleLogisticRegressionModelTrainer main = context.getBean(WekaSimpleLogisticRegressionModelTrainer.class);

		if (main != null) {
			main.train();
			context.close();
			System.exit(0);
		}
	}

}

