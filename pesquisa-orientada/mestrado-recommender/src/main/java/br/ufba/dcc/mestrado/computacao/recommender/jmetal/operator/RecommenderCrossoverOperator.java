package br.ufba.dcc.mestrado.computacao.recommender.jmetal.operator;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import br.ufba.dcc.mestrado.computacao.recommender.jmetal.solution.RecommenderSolution;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.util.RecommenderSolutionUtils;

@Component
public class RecommenderCrossoverOperator implements
		CrossoverOperator<List<RecommenderSolution>, List<RecommenderSolution>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 406730894338127011L;
	
	@Value("${crossover.probability}")
	private double crossoverProbability;
	
	private JMetalRandom randomGenerator;
	
	public RecommenderCrossoverOperator() {
		this.randomGenerator = JMetalRandom.getInstance();
	}

	@Override
	public List<RecommenderSolution> execute(List<RecommenderSolution> solutions) {

		if (solutions == null) {
			throw new JMetalException("Null parameter");
		} else if (solutions.size() != 2) {
			throw new JMetalException("There must be two parents instead of "
					+ solutions.size());
		}

		return doCrossover(solutions.get(0), solutions.get(1));

	}

	private List<RecommenderSolution> doCrossover(
			RecommenderSolution parent1,
			RecommenderSolution parent2) {
		
		List<RecommenderSolution> offspring = new ArrayList<RecommenderSolution>(2);

	    offspring.add(parent1.copy()) ;
	    offspring.add(parent2.copy()) ;
		
		if (randomGenerator.nextDouble() <= crossoverProbability) {
			for (int index = 0; index < parent1.getNumberOfVariables(); index++) {
				if (randomGenerator.nextDouble() <= 0.5) {
					RecommendedItem value1 = parent1.getVariableValue(index);
					RecommendedItem value2 = parent2.getVariableValue(index);

					if (! RecommenderSolutionUtils.containsRecommendedItem(offspring.get(0), value2) &&
							! RecommenderSolutionUtils.containsRecommendedItem(offspring.get(1), value1)) {
						offspring.get(0).setVariableValue(index, value2);
						offspring.get(1).setVariableValue(index, value1);
					}
				}
			}
		}
		
		return offspring;
	}


}
