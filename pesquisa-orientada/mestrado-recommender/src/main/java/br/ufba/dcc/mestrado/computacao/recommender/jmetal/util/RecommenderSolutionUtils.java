package br.ufba.dcc.mestrado.computacao.recommender.jmetal.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import br.ufba.dcc.mestrado.computacao.recommender.jmetal.solution.RecommenderSolution;

public class RecommenderSolutionUtils {
	
	/**
	 * 
	 * @param solution
	 * @param item
	 * @return
	 */
	public static boolean containsRecommendedItem(RecommenderSolution solution, RecommendedItem item) {
		
		if (item != null && solution != null) {
			for (int index = 0; index < solution.getNumberOfVariables(); index++) {
				RecommendedItem chromossome = solution.getVariableValue(index);
				if (chromossome != null && chromossome.getItemID() == item.getItemID()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param solution
	 * @return
	 */
	public static List<RecommendedItem> extractRecommendedItems(RecommenderSolution solution) {
		List<RecommendedItem> recommendedItems = new ArrayList<RecommendedItem>();
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			if (solution.getVariableValue(i) != null) {
				recommendedItems.add(solution.getVariableValue(i));
			}
		}
		return recommendedItems;
	}

}
