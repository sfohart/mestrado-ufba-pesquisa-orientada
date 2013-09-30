package br.ufba.dcc.mestrado.computacao.recommender;

import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public class DefaultRecommendedItemAggregatorStrategy implements
		RecommendedItemAggregatorStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4926870450511982819L;

	@Override
	public List<RecommendedItem> aggregateRecommendedItem(Map<Long, List<RecommendedItem>> recommendedItemMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecommendedItem> aggregateRecommendedItem(Map<Long, List<RecommendedItem>> recommendedItemMap, IDRescorer rescorer) {
		// TODO Auto-generated method stub
		return null;
	}

}
