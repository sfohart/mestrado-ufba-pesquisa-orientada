package br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.base;

import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;


/**
 * <p>
 * Implementations of this inner interface are simple helper classes which create a {@link MultiCriteriaRecommender} to be
 * evaluated based on the given Map<{@link RecommenderCriteriumEntity},{@link DataModel}>.
 * </p>
 */
public interface MultiCriteriaRecommenderBuilder {
	
	/**
	 * 
	 * @param dataModelMap
	 * @return
	 * @throws TasteException
	 */
	MultiCriteriaRecommender buildRecommender(Map<RecommenderCriteriumEntity,DataModel> dataModelMap) throws TasteException;

}
