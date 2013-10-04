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
		
		long startTime = System.currentTimeMillis();		
		
		Long userID = 7732L;
		logger.info(String.format("usuário de teste: id %d", userID));
		
		MultiCriteriaRecommender recommender = getService().buildMultiCriteriaRecommender(userID);
		long endTime = System.currentTimeMillis();
				
		logger.info(String.format("Tempo gasto construindo recommender: %d", endTime - startTime));
		
		
		
		startTime = System.currentTimeMillis();
		List<RecommendedItem> test = recommender.recommend(userID, 10);
		endTime = System.currentTimeMillis();
		
		logger.info(String.format("Tempo recomendando ítens: %d", endTime - startTime));
		
		if (test != null) {
			for (RecommendedItem item : test) {
				System.out.println(String.format("User ID %d, Item ID %d, Preference Value %f", userID, item.getItemID(), item.getValue()));
				
				Map<Long,Float> justify = recommender.justifyPreferenceValue(userID, item.getItemID());
				for (Map.Entry<Long,Float> entry : justify.entrySet()) {
					System.out.println(String.format("\tCriterium %d Value %f", entry.getKey(), entry.getValue()));
				}
			}
		}
		
		
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
