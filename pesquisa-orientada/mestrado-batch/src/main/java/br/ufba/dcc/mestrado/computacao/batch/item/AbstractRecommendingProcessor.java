package br.ufba.dcc.mestrado.computacao.batch.item;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import br.ufba.dcc.mestrado.computacao.batch.exception.MultiCriteriaBatchRecommenderException;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.RecommendationTypeEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.TagService;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.BaseRecommenderService;

public abstract class AbstractRecommendingProcessor implements ItemProcessor<UserEntity, UserRecommendationEntity> {
	
	private Logger logger = Logger.getLogger(AbstractRecommendingProcessor.class.getName());

	@Value("${recommender.results.sample.max}")
	protected Integer howManyItems;
	
	@Autowired
	private TagService tagService;
	
	protected BaseRecommenderService recommenderService;
	protected RecommendationTypeEnum recommendationType;
	
	public AbstractRecommendingProcessor(BaseRecommenderService recommenderService, RecommendationTypeEnum recommendationType) {
		this.recommenderService = recommenderService;
		this.recommendationType = recommendationType;
	}
	
	protected abstract List<RecommendedItem> recommendProjectsByUser(
			Long userId, 
			Integer howManyItems, 
			boolean filterInterestTags) throws TasteException;
	
	@Override
	public UserRecommendationEntity process(UserEntity user) throws Exception {
		
		UserRecommendationEntity userRecommendation = new UserRecommendationEntity();

		try {
		
			List<RecommendedItem> recommendedItems = recommendProjectsByUser(
							user.getId(), 
							howManyItems, 
							true);
			
			if (recommendedItems != null && ! recommendedItems.isEmpty()) {
				List<OpenHubProjectEntity> recommendedProjects = recommenderService.getRecommendedProjects(recommendedItems);
				
				userRecommendation.setUser(user);
				userRecommendation.setRecommendedProjects(recommendedProjects);
				userRecommendation.setRecommendationType(recommendationType);
				userRecommendation.setRecommendationDate(new Timestamp(System.currentTimeMillis()));
				
				
				//detached objects passed to persist? 
				if (user.getInterestTags() != null) {
					List<OpenHubTagEntity> interestTags = new ArrayList<OpenHubTagEntity>();
					for (OpenHubTagEntity tag : user.getInterestTags()) {
						OpenHubTagEntity newTag = tagService.findById(tag.getId());
						interestTags.add(newTag);
					}
					
					userRecommendation.setInterestTags(interestTags);
				}
				
				
				logger.info(String.format("Recomendações criadas para o usuário \"%s\", email \"%s\".", user.getLogin(), user.getEmail()));
			}
		
		} catch (TasteException ex) {
			logger.info(String.format("O usuário \"%s\",  email \"%s\",  não efetuou avaliações suficientes para que possamos fornecer recomendações.", 
					user.getLogin(), 
					user.getEmail()));
			throw new MultiCriteriaBatchRecommenderException(ex);
		}
		
		return userRecommendation;
	}
	
}
