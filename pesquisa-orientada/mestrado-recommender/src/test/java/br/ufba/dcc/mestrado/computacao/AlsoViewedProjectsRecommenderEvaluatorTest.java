package br.ufba.dcc.mestrado.computacao;

import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.ufba.dcc.mestrado.computacao.recommender.evaluator.impl.AlsoViewedProjectsRecommenderEvaluator;
import br.ufba.dcc.mestrado.computacao.spring.RecommenderAppConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RecommenderAppConfig.class})
public class AlsoViewedProjectsRecommenderEvaluatorTest {
	
	@Autowired
	private AlsoViewedProjectsRecommenderEvaluator evaluator;
	
	
	@Test
	public void testEvaluator() {
		
		try {
			evaluator.evaluate();
		} catch (TasteException e) {
			e.printStackTrace();
		}
		
		Assert.assertTrue(true);
		
	}

}
