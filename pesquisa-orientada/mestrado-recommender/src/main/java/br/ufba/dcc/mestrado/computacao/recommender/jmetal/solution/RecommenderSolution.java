package br.ufba.dcc.mestrado.computacao.recommender.jmetal.solution;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.uma.jmetal.solution.impl.AbstractGenericSolution;

import br.ufba.dcc.mestrado.computacao.recommender.jmetal.problem.RecommenderProblem;

public class RecommenderSolution extends
		AbstractGenericSolution<RecommendedItem, RecommenderProblem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7185400857215194183L;

	public RecommenderSolution(RecommenderProblem problem)  {
		super(problem);

		Integer howManyRecommendations = problem.getNumberOfVariables();
		
		try {
			List<RecommendedItem> recommendedItems = problem
					.getRecommender().recommend(
							problem.getUser().getId(), 
							howManyRecommendations);
			
			Collections.sort(recommendedItems, new Comparator<RecommendedItem>() {
				@Override
				public int compare(RecommendedItem o1, RecommendedItem o2) {
					Float value1 = o1.getValue();
					Float value2 = o2.getValue();
					
					return value1.compareTo(value2);
				}
			});
			
			Collections.reverse(recommendedItems);
			
			if (recommendedItems != null) {
				for (int i = 0; i < recommendedItems.size(); i++) {
					setVariableValue(i, recommendedItems.get(i));
				}
			}
		} catch (TasteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Construtor de cópia
	 * @param solution
	 */
	public RecommenderSolution(RecommenderSolution solution) {
	    super(solution.problem) ;

	    for (int i = 0; i < problem.getNumberOfVariables(); i++) {
	      setVariableValue(i, solution.getVariableValue(i));
	    }

	    for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
	      setObjective(i, solution.getObjective(i)) ;
	    }

	    overallConstraintViolationDegree = solution.overallConstraintViolationDegree ;
	    numberOfViolatedConstraints = solution.numberOfViolatedConstraints ;

	    attributes = new HashMap<Object,Object>(solution.attributes) ;
	  }

	@Override
	public String getVariableValueString(int index) {
		RecommendedItem recommendedItem = getVariableValue(index);
		return String.format("Item: %d | Value: %f", recommendedItem.getItemID(), recommendedItem.getValue());
	}

	@Override
	public RecommenderSolution copy() {
		return new RecommenderSolution(this);
	}

	public RecommenderProblem getProblem() {
		return problem;
	}
	
	

}
