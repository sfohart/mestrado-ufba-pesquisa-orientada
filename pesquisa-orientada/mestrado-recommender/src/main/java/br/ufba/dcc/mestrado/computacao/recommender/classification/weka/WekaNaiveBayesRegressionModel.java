package br.ufba.dcc.mestrado.computacao.recommender.classification.weka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.spring.RecommenderAppConfig;

@Component
public class WekaNaiveBayesRegressionModel extends AbstractWekaModelTrainer {

	@Autowired
	public WekaNaiveBayesRegressionModel(
			RatingByCriteriumService ratingByCriteriumService,
			RecommenderCriteriumService recommenderCriteriumService,
			OverallRatingService overallRatingService) {
		
		super(ratingByCriteriumService, recommenderCriteriumService, overallRatingService);
		this.setUseNumericToNominalFilter(true);
	}
	
	@Override
	protected Classifier initializeClassifier() throws Exception {
		Classifier naiveBayes = new NaiveBayes();
		return naiveBayes;
	}
	
	public static void main(String[] args) throws Exception {		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RecommenderAppConfig.class);

		WekaNaiveBayesRegressionModel main = context.getBean(WekaNaiveBayesRegressionModel.class);

		if (main != null) {
			main.train();
			context.close();
			System.exit(0);
		}
	}



	
	
}
