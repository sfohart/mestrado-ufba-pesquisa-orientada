package br.ufba.dcc.mestrado.computacao.recommender.jmetal.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.recommender.RandomRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.solution.RecommenderSolution;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.util.RecommenderSolutionUtils;
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
	
	private RandomRecommender randomRecommender;
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
		
		double accuracy = evaluateAccuracy(solution);
		double novelty = evaluateNovelty(solution);
		double diversity = evaluateDiversity(solution);
		
		solution.setObjective(0, accuracy);
		solution.setObjective(1, novelty);
		solution.setObjective(2, diversity);
	}

	
	private void buildRecommender() {
		try {
			this.randomRecommender = new RandomRecommender(getDataModel());
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
	public double evaluateDiversity(RecommenderSolution solution) {
		
		List<RecommendedItem> recommendedItems = RecommenderSolutionUtils.extractRecommendedItems(solution);
		
		double intraSimilarity = 0;
	
		try {
			if (itemSimilarity != null &&  recommendedItems != null && ! recommendedItems.isEmpty()) {
				
				int size = recommendedItems.size();
				
				double sumSimilarity = 0;
				
				for (RecommendedItem alpha : recommendedItems) {
					for (RecommendedItem beta : recommendedItems) {
						if (alpha != beta) {
							sumSimilarity += itemSimilarity.itemSimilarity(alpha.getItemID(), beta.getItemID());
						}
					}
				}
				
				intraSimilarity = sumSimilarity / ( size * ( size - 1 ) );
			}
		} catch (TasteException e) {
			e.printStackTrace();
		}
		
		/*if (intraSimilarity > 0) {
			return 1 / intraSimilarity;
		} else {
			return 1;
		}*/
		
		return - Math.log((intraSimilarity + 1) / 2);
	}

	/**
	 * 
	 * Lü, Linyuan, et al. "Recommender systems." Physics Reports 519.1 (2012): 1-49.
	 * {@link http://arxiv.org/pdf/1202.1112.pdf}
	 * 
	 * @param solution
	 * @return
	 */
	public double evaluateNovelty(RecommenderSolution solution) {
		
		List<RecommendedItem> recommendedItems = RecommenderSolutionUtils.extractRecommendedItems(solution);
		
		double popularity = 0;
		
		if (dataModel != null) {
			double sumDegrees = 0;
			for (RecommendedItem recommendedItem : recommendedItems) {
				sumDegrees += dataModel.getNumUsersWithPreferenceFor(recommendedItem.getItemID());
			}
			
			popularity = sumDegrees / (recommendedItems.size() * dataModel.getNumUsers());
		}
		
		
		/*if (popularity > 0) {
			return 1 / popularity;
		} else {
			return 1;
		}*/
		
		return - Math.log(popularity);
	}

	public double evaluateAccuracy(RecommenderSolution solution) {
		
		List<RecommendedItem> recommendedItems = RecommenderSolutionUtils.extractRecommendedItems(solution);
				
		double result = 0;
		
		if (recommendedItems != null && ! recommendedItems.isEmpty()) {
			for (RecommendedItem item : recommendedItems) {
				result += item.getValue();
			}
			
			result /= recommendedItems.size();
		}
		
		return result;
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
	
	public RandomRecommender getRandomRecommender() {
		return randomRecommender;
	}
}
