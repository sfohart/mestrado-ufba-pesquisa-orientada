package br.ufba.dcc.mestrado.computacao.service.recommender.impl;

import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.aggregatefunction.impl.SumAggregatePreferenceStrategy;
import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.base.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.impl.MultiCriteriaPreditionBasedRecommender;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.MultiCriteriaRecommenderService;

@Service(MultiCriteriaPredictionBasedRecommenderServiceImpl.BEAN_NAME)
public class MultiCriteriaPredictionBasedRecommenderServiceImpl 
		extends AbstractMultiCriteriaRecommenderServiceImpl 
		implements MultiCriteriaRecommenderService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7689049174557824929L;
	public static final String BEAN_NAME = "multiCriteriaPredictionBasedRecommenderService" ;

	
	@Override
	public MultiCriteriaRecommender buildMultiCriteriaRecommender(RecommenderBuilder recommenderBuilder) throws TasteException {
		Map<RecommenderCriteriumEntity, DataModel> dataModelMap = buildDataModelMap();
		
		return new MultiCriteriaPreditionBasedRecommender(
				recommenderBuilder, 
				new SumAggregatePreferenceStrategy(),
				dataModelMap);
	}

}
