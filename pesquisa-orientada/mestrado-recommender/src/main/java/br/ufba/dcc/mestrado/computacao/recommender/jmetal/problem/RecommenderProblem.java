package br.ufba.dcc.mestrado.computacao.recommender.jmetal.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.recommender.RandomRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.solution.RecommenderSolution;
import br.ufba.dcc.mestrado.computacao.service.core.base.BaseColaborativeFilteringService;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.mahout.base.MahoutDataModelService;
import br.ufba.dcc.mestrado.computacao.service.mahout.impl.MahoutColaborativeFilteringServiceImpl;

@Component
public class RecommenderProblem extends AbstractGenericProblem<RecommenderSolution> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1163943765380138333L;
	
	private UserEntity user;
	
	@Autowired
	@Qualifier(MahoutColaborativeFilteringServiceImpl.BEAN_NAME)
	private BaseColaborativeFilteringService colaborativeFilteringService;
	
	@Autowired
	private MahoutDataModelService mahoutDataModelService;
	
	@Autowired
	private OverallRatingService overallRatingService;
	
	private Recommender recommender;
	private GenericDataModel dataModel;
	
	private List<Long> projectList;
	
	private ItemSimilarity itemSimilarity;
	
	public RecommenderProblem() {
		this.setName("FLOSS Recommendation");
		
		setNumberOfVariables(10);
		setNumberOfObjectives(3);
	}
	
	public void initializeProblem() {
		if (dataModel == null) {
			buildDataModel();
			buildRecommender();
			itemSimilarity = new LogLikelihoodSimilarity(dataModel);
		}
	}

	@Override
	public void evaluate(RecommenderSolution solution) {
		
		List<RecommendedItem> recommendedItems = new ArrayList<RecommendedItem>();
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			if (solution.getVariableValue(i) != null) {
				recommendedItems.add(solution.getVariableValue(i));
			}
		}
		
		double accuracy = evaluateAccuracy(recommendedItems);
		double novelty = evaluateNovelty(recommendedItems);
		double diversity = evaluationDiversity(recommendedItems);
		
		solution.setObjective(0, accuracy);
		solution.setObjective(1, novelty);
		solution.setObjective(2, diversity);
	}
	
	private void buildRecommender() {
				
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
				
				Recommender recommender = new RandomRecommender(dataModel);					
				return recommender;
			}
		};
		
		try {
			this.recommender = recommenderBuilder.buildRecommender(getDataModel());
		} catch (TasteException e) {
			e.printStackTrace();
		}
		
	}

	private void buildDataModel() {
		Map<ImmutablePair<Long, Long>, Double> ratingsMap = getOverallRatingService().findAllLastOverallPreferenceValue();
		
		if (ratingsMap != null) {
			List<Preference> preferenceList = new ArrayList<Preference>();
			projectList = new ArrayList<Long>();
			
			for (ImmutablePair<Long, Long> userItemPair : ratingsMap.keySet()) {
				
				Double preferenceValue = ratingsMap.get(userItemPair);
				
				Preference preference = new GenericPreference(
						userItemPair.getLeft(),
						userItemPair.getRight(),
						preferenceValue.floatValue()
						);
				
				if (! projectList.contains(userItemPair.getRight())) {
					projectList.add(userItemPair.getRight());
				}
				
				preferenceList.add(preference);
			}
			
			dataModel = getMahoutDataModelService().buildDataModelByUser(preferenceList);
		}
	}

	/**
	 * 
	 * Lü, Linyuan, et al. "Recommender systems." Physics Reports 519.1 (2012): 1-49.
	 * {@link http://arxiv.org/pdf/1202.1112.pdf}
	 * 
	 * @param solution
	 * @return
	 */
	private double evaluationDiversity(List<RecommendedItem> recommendedItems) {
		
		double intraDiversity = 0;
	
		try {
			if (itemSimilarity != null) {
				double similarity = 0;
				
				for (RecommendedItem alpha : recommendedItems) {
					for (RecommendedItem beta : recommendedItems) {
						if (alpha != beta) {
							similarity += itemSimilarity.itemSimilarity(alpha.getItemID(), beta.getItemID());
						}
					}
				}
				
				intraDiversity = 1 / recommendedItems.size();
				intraDiversity /= recommendedItems.size() - 1;
				intraDiversity *= similarity;
			}
		} catch (TasteException e) {
			e.printStackTrace();
		}
		
		return intraDiversity;
	}

	/**
	 * 
	 * Lü, Linyuan, et al. "Recommender systems." Physics Reports 519.1 (2012): 1-49.
	 * {@link http://arxiv.org/pdf/1202.1112.pdf}
	 * 
	 * @param solution
	 * @return
	 */
	private double evaluateNovelty(List<RecommendedItem> recommendedItems) {
		
		double novelty = 0;
		
		if (dataModel != null) {
			double sumDegrees = 0;
			for (RecommendedItem recommendedItem : recommendedItems) {
				sumDegrees += dataModel.getNumUsersWithPreferenceFor(recommendedItem.getItemID());
			}
			
			novelty = sumDegrees / (recommendedItems.size() * dataModel.getNumUsers());
		}
		
		return novelty;
	}

	private double evaluateAccuracy(List<RecommendedItem> recommendedItems) {
		int num = 0;
		double sum = 0;
		
		for (int index = 0; index < getNumberOfVariables(); index++) {
			RecommendedItem item = recommendedItems.get(index);
			if (item != null) {
				num++;
				
				try {
					Float userItemPref = getDataModel().getPreferenceValue(
							getUser().getId(), 
							item.getItemID());
					
					float realPreference = userItemPref != null ? userItemPref : 0;
					float estimatedPreference = item.getValue();
					
					sum += Math.abs(realPreference - estimatedPreference);
					
				} catch (TasteException e) {
					e.printStackTrace();
				}
			}
		}
		
		double meanAbsoluteError = 0;
		if (num > 0) {
			meanAbsoluteError = sum / num;
		}
		
		return meanAbsoluteError;
	}

	@Override
	public RecommenderSolution createSolution() {
		return new RecommenderSolution(this);
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}


	public BaseColaborativeFilteringService getColaborativeFilteringService() {
		return colaborativeFilteringService;
	}

	public void setColaborativeFilteringService(
			BaseColaborativeFilteringService colaborativeFilteringService) {
		this.colaborativeFilteringService = colaborativeFilteringService;
	}

	public MahoutDataModelService getMahoutDataModelService() {
		return mahoutDataModelService;
	}

	public void setMahoutDataModelService(
			MahoutDataModelService mahoutDataModelService) {
		this.mahoutDataModelService = mahoutDataModelService;
	}

	public OverallRatingService getOverallRatingService() {
		return overallRatingService;
	}

	public void setOverallRatingService(OverallRatingService overallRatingService) {
		this.overallRatingService = overallRatingService;
	}

	public List<Long> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Long> projectList) {
		this.projectList = projectList;
	}
	
	public GenericDataModel getDataModel() {
		return dataModel;
	}
	
	public Recommender getRecommender() {
		return recommender;
	}
}
