package br.ufba.dcc.mestrado.computacao.service.spark.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.core.impl.BaseColaborativeFilteringServiceImpl;
import br.ufba.dcc.mestrado.computacao.service.spark.base.SparkColaborativeFilteringService;



@Service(SparkColaborativeFilteringServiceImpl.BEAN_NAME)
public class SparkColaborativeFilteringServiceImpl 
		extends BaseColaborativeFilteringServiceImpl
		implements SparkColaborativeFilteringService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1677831621087414952L;

	public static final String BEAN_NAME =  "sparkColaborativeFilteringService";
	
/*
	public void teste() {
		SparkConf sparkConf = new SparkConf(true);
		
		JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
		
		//obtendo lista de pares (userId,projectId) relacionado aos projetos que os usuários visualizaram
		final List<ImmutablePair<UserEntity, OhLohProjectEntity>> pageViewList = getProjectDetailPageViewRepository().findAllProjectDetailViews();
		
		//transformamndo em JavaRDD
		JavaRDD<ImmutablePair<UserEntity, OhLohProjectEntity>> immutablePairRDD = javaSparkContext.parallelize(pageViewList);
		
		//criando função para mapear ImmutablePair em Tuple2
		FlatMapFunction<ImmutablePair<UserEntity, OhLohProjectEntity>, Rating> flatMapFunction = new FlatMapFunction<ImmutablePair<UserEntity, OhLohProjectEntity>, Rating>() {

			*//**
			 * 
			 *//*
			private static final long serialVersionUID = -1040695800881356068L;

			@Override
			public Iterable<Rating> call(ImmutablePair<UserEntity, OhLohProjectEntity> pair)
					throws Exception {
				
				Rating rating = new Rating(pair.getLeft().getId().intValue(), pair.getRight().getId().intValue(), 5.0d);
				
				return Arrays.asList(rating);
			}
		};
		
		//utilizando função de mapeamento
		JavaRDD<Rating> ratings = immutablePairRDD.flatMap(flatMapFunction);
		
		//matrix de factorização
		MatrixFactorizationModel model =  ALS.trainImplicit(ratings.rdd(), 1, 20);
		
		
	}
*/
	@Transactional(readOnly = true)
	protected List<OhLohProjectEntity> recommendRatingProjectsByUser(
			Long userId,
			Integer howManyItems,
			boolean filterInterestTags,
			Map<Long, Map<ImmutablePair<Long, Long>, Double>> ratingsMap) {
		throw new NotImplementedException();
	}
	
	@Transactional(readOnly = true)
	protected List<OhLohProjectEntity> recommendViewedProjectsByItem(
			Long itemId,
			Integer howManyItems,			
			List<ImmutablePair<UserEntity, OhLohProjectEntity>> pageViewList) {
		throw new NotImplementedException();
	}

	@Transactional(readOnly = true)
	protected List<OhLohProjectEntity> recommendViewedProjectsByUser(
			Long userId,
			Integer howManyItems,
			boolean filterInterestTags,
			List<ImmutablePair<UserEntity, OhLohProjectEntity>> pageViewList) {
		throw new NotImplementedException();
	}


}
