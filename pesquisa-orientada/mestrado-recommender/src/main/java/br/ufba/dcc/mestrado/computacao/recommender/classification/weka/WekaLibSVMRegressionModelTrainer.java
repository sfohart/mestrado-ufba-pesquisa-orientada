package br.ufba.dcc.mestrado.computacao.recommender.classification.weka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.spring.RecommenderAppConfig;

@Component
public class WekaLibSVMRegressionModelTrainer extends AbstractWekaModelTrainer {
	
	@Autowired
	public WekaLibSVMRegressionModelTrainer(
			RatingByCriteriumService ratingByCriteriumService,
			RecommenderCriteriumService recommenderCriteriumService,
			OverallRatingService overallRatingService) {
		
		super(ratingByCriteriumService, recommenderCriteriumService, overallRatingService);
		this.setUseNumericToNominalFilter(true);
	}

	@Override
	protected Classifier initializeClassifier() throws Exception {
		
		Classifier libSVMFunction = new LibSVM();
		String[] options = "-S 0 -K 2 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 40.0 -C 1.0 -E 0.001 -P 0.1 -seed 1".split(" ");
		//String[] options = "-S 3 -K 2 -D 3 -G 0.1363134665831572 -R 0.0 -N 0.5 -M 40.0 -C 1.0 -E 0.001 -P 0.1 -seed 1".split(" ");
				
		libSVMFunction.setOptions(options);
		
		return libSVMFunction;
	}

	
	public static void main(String[] args) throws Exception {		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RecommenderAppConfig.class);

		WekaLibSVMRegressionModelTrainer main = context.getBean(WekaLibSVMRegressionModelTrainer.class);

		if (main != null) {
			main.train();
			context.close();
			System.exit(0);
		}
	}
}
