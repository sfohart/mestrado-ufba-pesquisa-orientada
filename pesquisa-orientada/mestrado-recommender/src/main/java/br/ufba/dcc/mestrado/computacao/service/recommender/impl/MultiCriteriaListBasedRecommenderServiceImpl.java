package br.ufba.dcc.mestrado.computacao.service.recommender.impl;

import java.util.Map;

import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.base.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.recommender.multicriteria.algorithm.impl.MultiCriteriaListBasedRecommender;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.MultiCriteriaRecommenderService;

@Service(MultiCriteriaListBasedRecommenderServiceImpl.BEAN_NAME)
public class MultiCriteriaListBasedRecommenderServiceImpl 
		extends AbstractMultiCriteriaRecommenderServiceImpl 
		implements MultiCriteriaRecommenderService {

	public static final String BEAN_NAME = "multiCriteriaListBasedRecommenderService" ;
	
	
	@Override
	public MultiCriteriaRecommender buildMultiCriteriaRecommender(RecommenderBuilder recommenderBuilder) {
		Map<RecommenderCriteriumEntity, DataModel> dataModelMap = buildDataModelMap();
		
		return new MultiCriteriaListBasedRecommender(recommenderBuilder, dataModelMap);
	}

}
