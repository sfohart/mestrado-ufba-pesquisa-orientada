package br.ufba.dcc.mestrado.computacao.batch.item;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.batch.exception.MultiCriteriaBatchRecommenderException;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.RecommendationEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.mahout.base.MultiCriteriaRecommenderService;
import br.ufba.dcc.mestrado.computacao.service.mahout.impl.MultiCriteriaListBasedRecommenderServiceImpl;

@Component
public class UserProcessor implements ItemProcessor<UserEntity, UserRecommendationEntity> {

	private Logger logger = Logger.getLogger(UserProcessor.class.getName());
	
	@Value("${recommender.results.sample.max}")
	private Integer howManyItems;
	
	@Autowired
	@Qualifier(MultiCriteriaListBasedRecommenderServiceImpl.BEAN_NAME)
	private MultiCriteriaRecommenderService multiCriteriaRecommenderService;
	
	@Override
	public UserRecommendationEntity process(UserEntity user) throws Exception {
		
		UserRecommendationEntity userRecommendation = new UserRecommendationEntity();

		try {
		
			List<RecommendedItem> recommendedItems = multiCriteriaRecommenderService
					.recommendRatingProjectsByUser(
							user.getId(), 
							howManyItems, 
							true);
			
			if (recommendedItems != null && ! recommendedItems.isEmpty()) {
				List<OpenHubProjectEntity> recommendedProjects = multiCriteriaRecommenderService.getRecommendedProjects(recommendedItems);
				
				userRecommendation.setUser(user);
				userRecommendation.setRecommendedProjects(recommendedProjects);
				userRecommendation.setRecommendationType(RecommendationEnum.MULTICRITERIA_LIST_BASED_RECOMMENDATION);
				userRecommendation.setRecommendationDate(new Timestamp(System.currentTimeMillis()));
				
				logger.info(String.format("Recomendações criadas para o usuário \"%s\".", user.getLogin()));
			}
		
		} catch (TasteException ex) {
			logger.info(String.format("O usuário \"%s\" não efetuou avaliações suficientes para que possamos fornecer recomendações.", user.getLogin()));
			throw new MultiCriteriaBatchRecommenderException(ex);
		}
		
		return userRecommendation;
	}

}
