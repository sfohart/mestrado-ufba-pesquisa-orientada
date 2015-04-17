package br.ufba.dcc.mestrado.computacao.recommender.jmetal.algorithm;

import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2GenericsBuilder;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import br.ufba.dcc.mestrado.computacao.recommender.jmetal.operator.RecommenderCrossoverOperator;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.operator.RecommenderMutationOperator;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.problem.RecommenderProblem;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.solution.RecommenderSolution;

public class MOEARecommenderBuilder extends
		SPEA2GenericsBuilder<RecommenderSolution> {

	public MOEARecommenderBuilder(
			RecommenderProblem problem,
			RecommenderCrossoverOperator crossoverOperator,
			RecommenderMutationOperator mutationOperator) {

		super(problem, crossoverOperator, mutationOperator);

		setSelectionOperator(new BinaryTournamentSelection());
		setSolutionListEvaluator(new SequentialSolutionListEvaluator<RecommenderSolution>());
	}

}
