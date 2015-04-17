package br.ufba.dcc.mestrado.computacao.recommender.jmetal.operator;

import java.util.List;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import br.ufba.dcc.mestrado.computacao.recommender.jmetal.solution.RecommenderSolution;

public class RecommenderMutationOperator implements
		MutationOperator<RecommenderSolution> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2785817743786065700L;

	private double mutationProbability;
	private JMetalRandom randomGenerator;
	private List<Long> projectList;

	public RecommenderMutationOperator(double probability, List<Long> projectList) {

		if (probability < 0) {
			throw new JMetalException("Mutation probability is negative: " + mutationProbability);
		}

		this.mutationProbability = probability;
		this.randomGenerator = JMetalRandom.getInstance();
		this.projectList = projectList;
	}

	@Override
	public RecommenderSolution execute(RecommenderSolution solution) {
		if (null == solution) {
			throw new JMetalException("Null solution parameter");
		}

		doMutation(solution);

		return solution;
	}

	private void doMutation(RecommenderSolution solution) {
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			if (randomGenerator.nextDouble() <= mutationProbability) {
				int projectIndex = randomGenerator.nextInt(0, projectList.size());
				solution.setVariableValue(i, projectList.get(projectIndex).intValue());
			}
		}
	}

}
