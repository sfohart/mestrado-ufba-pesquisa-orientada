package br.ufba.dcc.mestrado.computacao.main;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaRecommender;
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
		
		MultiCriteriaRecommender recommender = getService().buildMultiCriteriaRecommender();
		
		/*DataModelBuilder modelBuilder = new DataModelBuilder() {
			@Override
			public DataModel buildDataModel(FastByIDMap<PreferenceArray> trainingData) {
				return new GenericDataModel(trainingData);
			}
		};*/
				
		/*Random random = new Random(System.currentTimeMillis());
		int skip = random.nextInt(recommender.getNumUsers() - 1);*/
		
		Long userID = 7732L;
		
		/*LongPrimitiveIterator userIdIterator = recommender.getUserIDs();
		for (int i = 0; i < skip; i++) {
			userID = userIdIterator.next();
		}*/
		
		logger.info(String.format("usuário de teste: id %d", userID));
		
		List<RecommendedItem> test = recommender.recommend(userID, 10);
		
		if (test != null) {
			for (RecommendedItem item : test) {
				System.out.println(String.format("User ID %d, Item ID %d, Preference Value %f", userID, item.getItemID(), item.getValue()));
				
				Map<Long,Float> justify = recommender.justifyRecommendation(userID, item.getItemID());
				for (Map.Entry<Long,Float> entry : justify.entrySet()) {
					System.out.println(String.format("\tCriterium %d Value %f", entry.getKey(), entry.getValue()));
				}
			}
		}
		
		/*RecommenderEvaluator meanAverageErrorEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
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
        }*/
	}
	
	public static void main(String[] args) throws TasteException {
		AnnotationConfigApplicationContext context = 
		          new AnnotationConfigApplicationContext(RecommenderAppConfig.class);
		
		Main main = context.getBean(Main.class);
		
		if (main != null) {
			main.run();
		}
		
		context.close();
	}

}
