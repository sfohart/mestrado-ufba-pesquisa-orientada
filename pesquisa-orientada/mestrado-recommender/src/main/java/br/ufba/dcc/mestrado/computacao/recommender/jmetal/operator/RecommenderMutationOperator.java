package br.ufba.dcc.mestrado.computacao.recommender.jmetal.operator;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import br.ufba.dcc.mestrado.computacao.recommender.jmetal.solution.RecommenderSolution;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.util.RecommenderSolutionUtils;

@Component
public class RecommenderMutationOperator implements
		MutationOperator<RecommenderSolution> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2785817743786065700L;

	@Value("${mutation.probability}")
	private double mutationProbability;
	
	
	private JMetalRandom randomGenerator;

	public RecommenderMutationOperator() {
		this.randomGenerator = JMetalRandom.getInstance();
	}

	@Override
	public RecommenderSolution execute(RecommenderSolution solution) {
		if (null == solution) {
			throw new JMetalException("Null solution parameter");
		}
		
		if (solution.getProblem().getProjectList() == null) {
			throw new JMetalException("The problem does not define the list of all items available");
		}

		doMutation(solution);

		return solution;
	}

	private void doMutation(RecommenderSolution solution)  {
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			if (randomGenerator.nextDouble() <= mutationProbability) {
				Long userID = solution.getProblem().getUser().getId();
				
				RecommendedItem recommendedItem = null;
				
				try {
					do {
						recommendedItem = solution.getProblem()
								.getRandomRecommender()
								.recommend(userID, 1).get(0);
					} while (RecommenderSolutionUtils.containsRecommendedItem(solution, recommendedItem));
				} catch (TasteException e) {
					e.printStackTrace();
				}
				
				solution.setVariableValue(i, recommendedItem);
			}
		}
	}

}
