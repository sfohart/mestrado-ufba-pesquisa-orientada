
package br.ufba.dcc.mestrado.computacao.service.recommender.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.ColaborativeFilteringService;

public abstract class ColaborativeFilteringServiceImpl
		extends AbstractRecommenderServiceImpl
		implements ColaborativeFilteringService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -14257855122255993L;

	@Autowired
	protected ProjectDetailPageViewService projectDetailPageViewService;
	
	@Autowired
	protected RatingByCriteriumService ratingByCriteriumService;
	
	@Autowired
	protected RecommenderCriteriumService recommenderCriteriumService;
	
	@Autowired
	protected OverallRatingService overallRatingService;
	
	@Transactional(readOnly = true)
	protected Map<Long, Map<ImmutablePair<Long, Long>, Double>> findAllRatings() {
		List<RecommenderCriteriumEntity> criteriaList = recommenderCriteriumService.findAll();
		
		Map<Long, Map<ImmutablePair<Long, Long>, Double>> ratingMap = null;
		
		if (criteriaList != null && ! criteriaList.isEmpty()) {
			
			ratingMap = new TreeMap<Long, Map<ImmutablePair<Long,Long>,Double>>();
			
			for (RecommenderCriteriumEntity criterium : criteriaList) {
				Map<ImmutablePair<Long,Long>,Double> ratingMapByCriterium = ratingByCriteriumService.findAllLastPreferenceByCriterium(criterium.getId());
				ratingMap.put(criterium.getId(), ratingMapByCriterium);
			}
		}
				
		return ratingMap;
	}
	
	@Transactional(readOnly = true)
	protected Map<ImmutablePair<Long, Long>, Double> findAllRatingsByCriterium(Long criteriumId) {
		Map<ImmutablePair<Long, Long>, Double> ratingsByCriterium = null;
		
		if (criteriumId != null) {
			ratingsByCriterium = ratingByCriteriumService.findAllLastPreferenceByCriterium(criteriumId);
		}
		
		return ratingsByCriterium;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<RecommendedItem> recommendViewedProjectsByUser(
			Long userId, 
			Integer howManyItems) {
		return recommendViewedProjectsByUser(userId, howManyItems, false);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<RecommendedItem> recommendViewedProjectsByUser(
			Long userId, 
			Integer howManyItems,
			boolean filterInterestTags) {
		
		final List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList = projectDetailPageViewService.findAllProjectDetailViews();
		return recommendViewedProjectsByUser(userId, howManyItems, filterInterestTags, pageViewList);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<RecommendedItem> recommendViewedProjectsByItem(
			Long itemId, 
			Integer howManyItems) {
		final List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList = projectDetailPageViewService.findAllProjectDetailViews();
		return recommendViewedProjectsByItem(itemId, howManyItems, pageViewList);
	}
	
	@Transactional(readOnly = true)
	protected abstract List<RecommendedItem> recommendViewedProjectsByItem(
			Long itemId,
			Integer howManyItems,			
			List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList);

	@Transactional(readOnly = true)
	protected abstract List<RecommendedItem> recommendViewedProjectsByUser(
			Long userId,
			Integer howManyItems,
			boolean filterInterestTags,
			List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList);

	@Override
	@Transactional(readOnly = true)
	public List<RecommendedItem> recommendRatingProjectsByUser(Long userId, Integer howManyItems) {
		return recommendRatingProjectsByUser(userId,howManyItems,false);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<RecommendedItem> recommendRatingProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags) {
		
		final Map<Long, Map<ImmutablePair<Long, Long>, Double>> ratingsMap =
				findAllRatings();
		
		return recommendRatingProjectsByUser(userId, howManyItems, filterInterestTags, ratingsMap);
	}

	@Transactional(readOnly = true)
	protected abstract List<RecommendedItem> recommendRatingProjectsByUser(
			Long userId,
			Integer howManyItems,
			boolean filterInterestTags,
			Map<Long, Map<ImmutablePair<Long, Long>, Double>> ratingsMap);
	
	
	@Transactional(readOnly = true)
	public List<RecommendedItem> recommendRatingProjectsByUserAndCriterium(Long userId, Long criteriumId, Integer howManyItems) {
		return recommendRatingProjectsByUserAndCriterium(userId, criteriumId, howManyItems, false);
	}
	
	@Transactional(readOnly = true)
	public List<RecommendedItem> recommendRatingProjectsByUserAndCriterium(Long userId, Long criteriumId, Integer howManyItems, boolean filterInterestTags) {
		Map<ImmutablePair<Long, Long>, Double> ratingsByCriterium = findAllRatingsByCriterium(criteriumId);
		
		return recommendRatingProjectsByUserAndCriterium(userId, criteriumId, howManyItems, filterInterestTags, ratingsByCriterium);
	}
	
	protected abstract List<RecommendedItem> recommendRatingProjectsByUserAndCriterium(
			Long userId,
			Long criteriumId,
			Integer howManyItems,
			boolean filterInterestTags,
			Map<ImmutablePair<Long, Long>, Double> ratingsMap);
	
	
	public List<RecommendedItem> recommendRandomProjectsByUser(Long userId, Integer howManyItems) {
		return recommendRandomProjectsByUser(userId, howManyItems, false);
	}
	
	public List<RecommendedItem> recommendRandomProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags) {
		Map<ImmutablePair<Long, Long>, Double> ratingsMap = overallRatingService.findAllLastOverallPreferenceValue();
		
		return recommendRandomProjectsByUser(userId, howManyItems, filterInterestTags, ratingsMap);
	}
	
	protected abstract List<RecommendedItem> recommendRandomProjectsByUser(
			Long userId,
			Integer howManyItems,
			boolean filterInterestTags,
			Map<ImmutablePair<Long, Long>, Double> ratingsMap);
	
	


}

