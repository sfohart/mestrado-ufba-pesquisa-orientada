package br.ufba.dcc.mestrado.computacao.batch.item;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.service.base.UserRecommendationService;

@Component
public class UserRecommendationWriter implements ItemWriter<UserRecommendationEntity>{
	
	private Logger logger = Logger.getLogger(UserRecommendationWriter.class.getName());
	
	@Autowired
	private UserRecommendationService userRecommendationService;
	
	@Override
	public void write(List<? extends UserRecommendationEntity> userRecommendations) throws Exception {
		
		if (userRecommendations != null) {
			for (UserRecommendationEntity recommendation : userRecommendations) {
				if (recommendation.getRecommendedProjects() != null && ! recommendation.getRecommendedProjects().isEmpty()) {
					userRecommendationService.save(recommendation);
					logger.info(String.format("Recomendações para o usuário \"%s\" gravadas na base de dados", recommendation.getUser().getLogin()));
				}
			}
		}
	}

}
