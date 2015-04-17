package br.ufba.dcc.mestrado.computacao.recommender.jmetal.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.core.base.BaseColaborativeFilteringService;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.mahout.base.MahoutDataModelService;
import br.ufba.dcc.mestrado.computacao.service.mahout.impl.MahoutColaborativeFilteringServiceImpl;

public class RecommenderProblem extends AbstractIntegerProblem {

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
	
	
	private DataModel dataModel;
	
	private List<Long> projectList;
	
	private ItemSimilarity itemSimilarity;
	
	public RecommenderProblem() {
		this.setName("FLOSS Recommendation");
	}

	@Override
	public void evaluate(IntegerSolution solution) {
		
		if (dataModel == null) {
			buildDataModel();
			itemSimilarity = new LogLikelihoodSimilarity(dataModel);
		}
		
		List<Integer> projects = new ArrayList<Integer>();
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			if (solution.getVariableValue(i) != null) {
				projects.add(solution.getVariableValue(i));
			}
		}
		
		double accuracy = evaluateAccuracy(projects);
		double novelty = evaluateNovelty(projects);
		double diversity = evaluationDiversity(projects);
		
		solution.setObjective(0, accuracy);
		solution.setObjective(1, novelty);
		solution.setObjective(2, diversity);
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
	private double evaluationDiversity(List<Integer> projects) {
		
		double intraDiversity = 0;
	
		try {
			if (itemSimilarity != null) {
				double similarity = 0;
				
				for (Integer alpha : projects) {
					for (Integer beta : projects) {
						if (alpha != beta) {
							similarity += itemSimilarity.itemSimilarity(alpha.longValue(), beta.longValue());
						}
					}
				}
				
				intraDiversity = 1 / projects.size();
				intraDiversity /= projects.size() - 1;
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
	private double evaluateNovelty(List<Integer> projects) {
		
		double novelty = 0;
		
		try {
			if (dataModel != null) {
				double sumDegrees = 0;
				for (Integer id : projects) {
					sumDegrees += dataModel.getNumUsersWithPreferenceFor(id.longValue());
				}
				
				novelty = 1 / projects.size();
				novelty *= sumDegrees;
			}
		} catch (TasteException e) {
			e.printStackTrace();
		}
		
		return novelty;
	}

	private double evaluateAccuracy(List<Integer> projects) {
		return 0;
	}

	@Override
	public IntegerSolution createSolution() {
		return super.createSolution();
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
	

}
