package br.ufba.dcc.mestrado.computacao.main;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.recommender.CriteriumPreference;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderService;
import br.ufba.dcc.mestrado.computacao.spring.RecommenderAppConfig;

@Component
public class Main {
	
	private Logger logger = Logger.getLogger(Main.class.getName());
	private RecommenderService service;
	
	@Autowired
	public Main(RecommenderService service) {
		this.service = service;
	}
	
	protected RecommenderService getService() {
		return service;
	}

	public void run() throws TasteException {
		logger.info("Running");
		
		
		Long criteriumID = 1L;
		List<CriteriumPreference> preferencesList = getService().findAllByCriterium(criteriumID);
		
		final DataModel dataModel = getService().buildUserDataModel(preferencesList);
		
		RecommenderBuilder recommenderBuilder = getService().createItemBasedRecomenderBuilder(dataModel);
		DataModelBuilder modelBuilder = new DataModelBuilder() {
			@Override
			public DataModel buildDataModel(FastByIDMap<PreferenceArray> trainingData) {
				return new GenericDataModel(trainingData);
			}
		};
				
		Random random = new Random(System.currentTimeMillis());
		int skip = random.nextInt(dataModel.getNumUsers() - 1);
		
		Long userID = null;
		
		LongPrimitiveIterator userIdIterator = dataModel.getUserIDs();
		for (int i = 0; i < skip; i++) {
			userID = userIdIterator.next();
		}
		
		List<RecommendedItem> test = recommenderBuilder
				.buildRecommender(dataModel)
				.recommend(userID, 10);
		
		if (test != null) {
			for (RecommendedItem item : test) {
				System.out.println(String.format("User ID %d, Item ID %d, Preference Value %f", userID, item.getItemID(), item.getValue()));
			}
		}
		
		RecommenderEvaluator meanAverageErrorEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		RecommenderEvaluator rootMeanSquaredEvaluator = new RMSRecommenderEvaluator();
		
		RecommenderIRStatsEvaluator  evaluator2 = new GenericRecommenderIRStatsEvaluator();
		
		double meanAverageError = meanAverageErrorEvaluator.evaluate(recommenderBuilder, modelBuilder, dataModel, 0.95, 0.5);
		double rootMeanSquared = rootMeanSquaredEvaluator.evaluate(recommenderBuilder, modelBuilder, dataModel, 0.95, 0.5);
		
        System.out.println("mean average error: " + meanAverageError);
        System.out.println("root mean squared: " + rootMeanSquared);

        try {
            IRStatistics stats = evaluator2.evaluate(
                    recommenderBuilder, modelBuilder, dataModel, null, 2,
                    0.0,
                    0.5
            );
            System.out.println("recall: " + stats.getRecall());
            System.out.println("precision: " + stats.getPrecision());
        } catch (Throwable t) {
            System.out.println("throwing " + t);
        }
	}
	
	public static void main(String[] args) throws TasteException {
		ApplicationContext context = 
		          new AnnotationConfigApplicationContext(RecommenderAppConfig.class);
		
		Main main = context.getBean(Main.class);
		
		if (main != null) {
			main.run();
		}
	}

}
