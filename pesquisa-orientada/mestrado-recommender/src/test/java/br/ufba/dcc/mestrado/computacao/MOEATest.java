package br.ufba.dcc.mestrado.computacao;

import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.comparator.DominanceComparator;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.algorithm.MOEARecommenderBuilder;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.operator.RecommenderCrossoverOperator;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.operator.RecommenderMutationOperator;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.problem.RecommenderProblem;
import br.ufba.dcc.mestrado.computacao.recommender.jmetal.solution.RecommenderSolution;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;
import br.ufba.dcc.mestrado.computacao.spring.RecommenderAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RecommenderAppConfig.class})
public class MOEATest {

	
	@Autowired
	private RecommenderMutationOperator mutationOperator;
	
	@Autowired
	private RecommenderCrossoverOperator crossoverOperator;
	
	@Autowired
	private RecommenderProblem recommenderProblem;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMOEARecommender() {
		
		UserEntity user = userService.findById(9L);
		
		recommenderProblem.initializeProblem();
		recommenderProblem.setUser(user);
		
		MOEARecommenderBuilder moeaRecommenderBuilder = new MOEARecommenderBuilder(recommenderProblem, crossoverOperator, mutationOperator);
		
		Algorithm<RecommenderSolution> algorithm = moeaRecommenderBuilder.build();
		algorithm.run();
		
		List<RecommenderSolution> solutionList = (List<RecommenderSolution>) algorithm.getResult();
		
		Assert.assertNotNull(solutionList);
		
		int indexBestSolution = SolutionListUtils.findIndexOfBestSolution(solutionList, new DominanceComparator());
		RecommenderSolution bestSolution = solutionList.get(indexBestSolution);

		for (int index = 0; index < bestSolution.getNumberOfVariables(); index++) {
			RecommendedItem recommendedItem = bestSolution.getVariableValue(index);
			if (recommendedItem != null) {
				OpenHubProjectEntity project = projectService.findById(recommendedItem.getItemID());
				System.out.println(String.format("Estimated Preference: %f | Project: \"%s\" (id = %d) ", recommendedItem.getValue(), project.getName(), project.getId()));
			}
		}
	}
	
}
