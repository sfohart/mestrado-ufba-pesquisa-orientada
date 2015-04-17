package br.ufba.dcc.mestrado.computacao.recommender.jmetal.operator;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import br.ufba.dcc.mestrado.computacao.recommender.jmetal.solution.RecommenderSolution;

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
				int projectIndex = randomGenerator.nextInt(0, solution.getProblem().getProjectList().size() - 1);
				
				Long itemID = solution.getProblem().getProjectList().get(projectIndex);
				Long userID = solution.getProblem().getUser().getId();
				
				try {
					float value = solution.getProblem().getRecommender().estimatePreference(userID, itemID);
					RecommendedItem recommendedItem = new GenericRecommendedItem(itemID, value);
					solution.setVariableValue(i, recommendedItem);
				} catch (TasteException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
