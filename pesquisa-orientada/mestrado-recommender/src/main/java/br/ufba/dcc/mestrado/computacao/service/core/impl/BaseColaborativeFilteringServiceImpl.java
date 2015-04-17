
package br.ufba.dcc.mestrado.computacao.service.core.impl;

import java.util.LinkedList;
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
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.core.base.BaseColaborativeFilteringService;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;

public abstract class BaseColaborativeFilteringServiceImpl implements
		BaseColaborativeFilteringService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -14257855122255993L;

	@Autowired
	private ProjectDetailPageViewService projectDetailPageViewService;
	
	@Autowired
	private RatingByCriteriumService ratingByCriteriumService;
	
	@Autowired
	private RecommenderCriteriumService recommenderCriteriumService;
	
	@Autowired
	private OverallRatingService overallRatingService;
	
	@Autowired
	private ProjectService projectService;

	public ProjectDetailPageViewService getProjectDetailPageViewService() {
		return projectDetailPageViewService;
	}

	public RatingByCriteriumService getRatingByCriteriumService() {
		return ratingByCriteriumService;
	}

	public RecommenderCriteriumService getRecommenderCriteriumService() {
		return recommenderCriteriumService;
	}

	public OverallRatingService getOverallRatingService() {
		return overallRatingService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	@Transactional(readOnly = true)
	protected Map<Long, Map<ImmutablePair<Long, Long>, Double>> findAllRatings() {
		List<RecommenderCriteriumEntity> criteriaList = getRecommenderCriteriumService().findAll();
		
		Map<Long, Map<ImmutablePair<Long, Long>, Double>> ratingMap = null;
		
		if (criteriaList != null && ! criteriaList.isEmpty()) {
			
			ratingMap = new TreeMap<Long, Map<ImmutablePair<Long,Long>,Double>>();
			
			for (RecommenderCriteriumEntity criterium : criteriaList) {
				Map<ImmutablePair<Long,Long>,Double> ratingMapByCriterium = getRatingByCriteriumService().findAllLastPreferenceByCriterium(criterium.getId());
				ratingMap.put(criterium.getId(), ratingMapByCriterium);
			}
		}
				
		return ratingMap;
	}
	
	@Transactional(readOnly = true)
	protected Map<ImmutablePair<Long, Long>, Double> findAllRatingsByCriterium(Long criteriumId) {
		Map<ImmutablePair<Long, Long>, Double> ratingsByCriterium = null;
		
		if (criteriumId != null) {
			ratingsByCriterium = getRatingByCriteriumService().findAllLastPreferenceByCriterium(criteriumId);
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
		
		final List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList = getProjectDetailPageViewService().findAllProjectDetailViews();
		return recommendViewedProjectsByUser(userId, howManyItems, filterInterestTags, pageViewList);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<RecommendedItem> recommendViewedProjectsByItem(
			Long itemId, 
			Integer howManyItems) {
		final List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList = getProjectDetailPageViewService().findAllProjectDetailViews();
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
		Map<ImmutablePair<Long, Long>, Double> ratingsMap = getOverallRatingService().findAllLastOverallPreferenceValue();
		
		return recommendRandomProjectsByUser(userId, howManyItems, filterInterestTags, ratingsMap);
	}
	
	protected abstract List<RecommendedItem> recommendRandomProjectsByUser(
			Long userId,
			Integer howManyItems,
			boolean filterInterestTags,
			Map<ImmutablePair<Long, Long>, Double> ratingsMap);
	
	
	@Transactional(readOnly = true)
	public List<OpenHubProjectEntity> getRecommendedProjects(List<RecommendedItem> recommendedItems) {
		List<OpenHubProjectEntity> projectList = null;
		
		if (recommendedItems != null) {
			projectList = new LinkedList<OpenHubProjectEntity>();
			
			for (RecommendedItem recommendedItem : recommendedItems) {
				OpenHubProjectEntity project = getProjectService().findById(recommendedItem.getItemID());
				projectList.add(project);
			}
		}
		
		return projectList;
		
	}

}

