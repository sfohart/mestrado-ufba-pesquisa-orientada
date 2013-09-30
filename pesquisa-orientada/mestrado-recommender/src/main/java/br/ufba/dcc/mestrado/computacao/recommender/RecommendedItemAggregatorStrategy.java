package br.ufba.dcc.mestrado.computacao.recommender;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

/**
 * 
 * @author leandro.ferreira
 *
 */
public interface RecommendedItemAggregatorStrategy extends Serializable {
	
	List<RecommendedItem> aggregateRecommendedItem(Map<Long, List<RecommendedItem>> recommendedItemMap);
	
	List<RecommendedItem> aggregateRecommendedItem(Map<Long, List<RecommendedItem>> recommendedItemMap, IDRescorer rescorer);
}
