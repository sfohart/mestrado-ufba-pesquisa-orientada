package br.ufba.dcc.mestrado.computacao.main;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaDataModelBuilder;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaRecommenderBuilder;
import br.ufba.dcc.mestrado.computacao.recommender.PreferenceAggregatorStrategy;
import br.ufba.dcc.mestrado.computacao.recommender.eval.AverageAbsoluteDifferenceMultiCriteriaRecommenderEvaluator;
import br.ufba.dcc.mestrado.computacao.recommender.eval.MultiCriteriaRecommenderEvaluator;
import br.ufba.dcc.mestrado.computacao.recommender.eval.RMSMultiCriteriaRecommenderEvaluator;
import br.ufba.dcc.mestrado.computacao.recommender.impl.DefaultMultiCriteriaDataModelBuilder;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderService;
import br.ufba.dcc.mestrado.computacao.spring.RecommenderAppConfig;

@Component
public class RecommenderMain {
	
	private Logger logger = Logger.getLogger(RecommenderMain.class.getName());
	private RecommenderService service;
	
	@Autowired
	public RecommenderMain(RecommenderService service) {
		this.service = service;
	}
	
	protected RecommenderService getService() {
		return service;
	}

	public void run() throws TasteException {
		logger.info("Running");
		
		
		Long userID = 88921L;
		logger.info(String.format("usuário de teste: id %d", userID));
		
		List<RecommenderCriteriumEntity> criteriaList = getService().findAllCriteria();
		FastByIDMap<DataModel> dataModelMap = getService().createUserDataModelMap(criteriaList);
		FastByIDMap<Float> criteriaWeightMap = getService().findUserCriteriaWeight(userID);
		
		MultiCriteriaRecommenderBuilder recommenderBuilder = getService().createMultiCriteriaRecommenderBuilder();
		
		long startTime = System.currentTimeMillis();		
		MultiCriteriaRecommender recommender = recommenderBuilder.buildRecommender(dataModelMap, criteriaWeightMap);
		long endTime = System.currentTimeMillis();
				
		logger.info(String.format("Tempo gasto construindo recommender parao usuário '%d': %d", userID, endTime - startTime));
				
		startTime = System.currentTimeMillis();
		List<RecommendedItem> test = recommender.recommend(userID, 10);		
		endTime = System.currentTimeMillis();
		
		logger.info(String.format("Tempo recomendando ítens para o usuário '%d': %d", userID, endTime - startTime));
		
		startTime = System.currentTimeMillis();
		if (test != null) {
			for (RecommendedItem item : test) {
				System.out.println(String.format("User ID %d, Item ID %d, Preference Value %f", userID, item.getItemID(), item.getValue()));
				
				FastByIDMap<Float> justify = recommender.justifyPreferenceValue(userID, item.getItemID());
				for (Map.Entry<Long,Float> entry : justify.entrySet()) {
					System.out.println(String.format("\tCriterium %d Value %f", entry.getKey(), entry.getValue()));
				}
			}
		}
		endTime = System.currentTimeMillis();
		logger.info(String.format("Tempo justificando recomendações: %d",  endTime - startTime));
		
		PreferenceAggregatorStrategy preferenceAggregatorStrategy = recommender.getPreferenceAggregatorStrategy();
		MultiCriteriaRecommenderEvaluator meanAverageErrorEvaluator = new AverageAbsoluteDifferenceMultiCriteriaRecommenderEvaluator(preferenceAggregatorStrategy);
		MultiCriteriaRecommenderEvaluator rootMeanSquaredEvaluator = new RMSMultiCriteriaRecommenderEvaluator(preferenceAggregatorStrategy);
		
		
		MultiCriteriaDataModelBuilder modelBuilder = new DefaultMultiCriteriaDataModelBuilder(); 
		
		
		
		double meanAverageError = meanAverageErrorEvaluator.evaluate(recommenderBuilder, modelBuilder, dataModelMap, criteriaWeightMap, 0.7, 1.0);
		double rootMeanSquared = rootMeanSquaredEvaluator.evaluate(recommenderBuilder, modelBuilder, dataModelMap, criteriaWeightMap, 0.7, 1.0);
		
        System.out.println("mean average error: " + meanAverageError);
        System.out.println("root mean squared: " + rootMeanSquared);
		
		
	}
	
	public static void main(String[] args) throws TasteException {
		AnnotationConfigApplicationContext context = 
		          new AnnotationConfigApplicationContext(RecommenderAppConfig.class);
		
		RecommenderMain recommenderMain = context.getBean(RecommenderMain.class);
		
		if (recommenderMain != null) {
			recommenderMain.run();
		}
		
		context.close();
	}

}
