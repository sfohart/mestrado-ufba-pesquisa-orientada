package br.ufba.dcc.mestrado.computacao.recommender.classification;

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
	}

	@Override
	protected Classifier initializeClassifier() throws Exception {
		Classifier simpleLogistic = new SimpleLogistic();
		
		String[] options = new String[8];
		
		options[0] = "-I";
		options[1] = "0";
		
		options[2] = "-M";
		options[3] = "500";
		
		options[4] = "-H";
		options[5] = "50";
		
		options[6] = "-W";
		options[7] = "-0.0";
		
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
