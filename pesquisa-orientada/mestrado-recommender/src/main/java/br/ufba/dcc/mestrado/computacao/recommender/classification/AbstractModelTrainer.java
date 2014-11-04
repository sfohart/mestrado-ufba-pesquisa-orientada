package br.ufba.dcc.mestrado.computacao.recommender.classification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;

public abstract class AbstractModelTrainer implements ModelTrainer {
	
	protected RatingByCriteriumService ratingByCriteriumService;
	
	protected RecommenderCriteriumService recommenderCriteriumService;
	
	protected OverallRatingService overallRatingService;
	
	@Value("${prediction.model.path}")
	private String modelPath;

	@Autowired
	public AbstractModelTrainer(
			RatingByCriteriumService ratingByCriteriumService,
			RecommenderCriteriumService recommenderCriteriumService,
			OverallRatingService overallRatingService) {
		
		super();
		
		this.ratingByCriteriumService = ratingByCriteriumService;
		this.recommenderCriteriumService = recommenderCriteriumService;
		this.overallRatingService = overallRatingService;
	}

	protected String getModelPath() {
		return modelPath;
	}

	protected void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}
	

}
