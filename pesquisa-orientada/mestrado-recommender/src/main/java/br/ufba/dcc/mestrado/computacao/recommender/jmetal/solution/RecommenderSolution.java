package br.ufba.dcc.mestrado.computacao.recommender.jmetal.solution;

import java.util.List;

import org.uma.jmetal.solution.impl.GenericIntegerSolution;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.problem.RecommenderProblem;

public class RecommenderSolution extends
		GenericIntegerSolution {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7185400857215194183L;

	protected RecommenderSolution(RecommenderProblem problem) {
		super(problem);

		UserEntity user = problem.getUser();
		Integer howManyRecommendations = problem.getNumberOfVariables();

		List<OpenHubProjectEntity> recommendations = problem
				.getColaborativeFilteringService()
				.recommendRandomProjectsByUser(user.getId(),
						howManyRecommendations);

		if (recommendations != null) {
			for (int i = 0; i < recommendations.size(); i++) {
				setVariableValue(i, recommendations.get(i).getId().intValue());
			}
		}

	}


}
