package br.ufba.dcc.mestrado.computacao.recommender.mahout.classification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.mahout.classifier.sgd.L2;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.common.RandomUtils;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;

@Component
public class LogisticRegression {
	
	@Autowired
	private RatingByCriteriumService ratingByCriteriumService;
	
	@Autowired
	private RecommenderCriteriumService recommenderCriteriumService;
	
	@Autowired
	private OverallRatingService overallRatingService;
	
	private OnlineLogisticRegression logisticRegression;

	
	@Autowired
	public LogisticRegression(
			RatingByCriteriumService ratingByCriteriumService,
			RecommenderCriteriumService recommenderCriteriumService,
			OverallRatingService overallRatingService) {
		super();
		this.ratingByCriteriumService = ratingByCriteriumService;
		this.recommenderCriteriumService = recommenderCriteriumService;
		this.overallRatingService = overallRatingService;
	}


	


	public void train() {
		List<Vector> vectorList = new ArrayList<>();
		
		List<RecommenderCriteriumEntity> criteriumList = recommenderCriteriumService.findAll();
		
		Map<ImmutablePair<Long, Long>, DenseVector> userMap = new HashMap<>();
		
		if (criteriumList != null) {
			for (RecommenderCriteriumEntity criterium : criteriumList) {
				Map<ImmutablePair<Long, Long>, Double> criteriumMap = ratingByCriteriumService.findAllLastPreferenceByCriterium(criterium.getId());
				if (criteriumMap != null) {
					for (Map.Entry<ImmutablePair<Long, Long>, Double> entry : criteriumMap.entrySet()) {
						DenseVector vector = userMap.get(entry.getKey());
						if (vector == null ){
							vector = new DenseVector();
							vector.set(0, entry.getKey().getLeft());
							vector.set(11, entry.getKey().getRight());
						}
						
						vector.set(criterium.getId().intValue(), entry.getValue());
						userMap.put(entry.getKey(), vector);
					}
				}
			}
		}
		
		List<Double> target = new ArrayList<>();
		List<DenseVector> data = new ArrayList<>(userMap.values());
		
		for (Map.Entry<ImmutablePair<Long, Long>, DenseVector> entry : userMap.entrySet()) {
			PreferenceEntity preference = overallRatingService
					.findLastOverallPreferenceByUserAndItem(
							entry.getKey().getLeft(), 
							entry.getKey().getRight());
			
			target.add(preference.getValue());
		}
		
		
		List<Integer> orderList = new ArrayList<>();
		for (int i = 0; i < userMap.size(); i++) {
			orderList.add(i);
		}
		
		Random random = RandomUtils.getRandom();
		Collections.shuffle(orderList, random);
		
		List<Integer> train = orderList.subList(0, Math.round(orderList.size() / 70));
		List<Integer> test = orderList.subList(Math.round(orderList.size() / 70), orderList.size());
		
		this.logisticRegression = new OnlineLogisticRegression(5, 12, new L2(1));
		for (int pass = 0; pass < 30; pass++ ){
			Collections.shuffle(train, random);
			for (int k : train) {
				logisticRegression.train(target.get(k).intValue(), data.get(k));
			}
		}
		
		
		
	}

}
