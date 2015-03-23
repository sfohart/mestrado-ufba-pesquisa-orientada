package br.ufba.dcc.mestrado.computacao.service.core.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.ProjectDetailPageViewRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.RatingByCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.service.core.base.BaseColaborativeFilteringService;

public abstract class BaseColaborativeFilteringServiceImpl implements
		BaseColaborativeFilteringService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -14257855122255993L;

	@Autowired
	private ProjectDetailPageViewRepository projectDetailPageViewRepository;
	
	@Autowired
	private RatingByCriteriumRepository ratingByCriteriumRepository;
	
	@Autowired
	private RecommenderCriteriumRepository recommenderCriteriumRepository;

	public ProjectDetailPageViewRepository getProjectDetailPageViewRepository() {
		return projectDetailPageViewRepository;
	}

	public void setProjectDetailPageViewRepository(
			ProjectDetailPageViewRepository projectDetailPageViewRepository) {
		this.projectDetailPageViewRepository = projectDetailPageViewRepository;
	}

	public RatingByCriteriumRepository getRatingByCriteriumRepository() {
		return ratingByCriteriumRepository;
	}

	public void setRatingByCriteriumRepository(
			RatingByCriteriumRepository ratingByCriteriumRepository) {
		this.ratingByCriteriumRepository = ratingByCriteriumRepository;
	}

	public RecommenderCriteriumRepository getRecommenderCriteriumRepository() {
		return recommenderCriteriumRepository;
	}

	public void setRecommenderCriteriumRepository(
			RecommenderCriteriumRepository recommenderCriteriumRepository) {
		this.recommenderCriteriumRepository = recommenderCriteriumRepository;
	}

	@Transactional(readOnly = true)
	protected Map<Long, Map<ImmutablePair<Long, Long>, Double>> findAllRatings() {
		List<RecommenderCriteriumEntity> criteriaList = getRecommenderCriteriumRepository().findAll();
		
		Map<Long, Map<ImmutablePair<Long, Long>, Double>> ratingMap = null;
		
		if (criteriaList != null && ! criteriaList.isEmpty()) {
			
			ratingMap = new TreeMap<Long, Map<ImmutablePair<Long,Long>,Double>>();
			
			for (RecommenderCriteriumEntity criterium : criteriaList) {
				Map<ImmutablePair<Long,Long>,Double> ratingMapByCriterium = getRatingByCriteriumRepository().findAllLastPreferenceByCriterium(criterium.getId());
				ratingMap.put(criterium.getId(), ratingMapByCriterium);
			}
		}
				
		return ratingMap;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OpenHubProjectEntity> recommendViewedProjectsByUser(
			Long userId, 
			Integer howManyItems) {
		return recommendViewedProjectsByUser(userId, howManyItems, false);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OpenHubProjectEntity> recommendViewedProjectsByUser(
			Long userId, 
			Integer howManyItems,
			boolean filterInterestTags) {
		
		final List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList = getProjectDetailPageViewRepository().findAllProjectDetailViews();
		return recommendViewedProjectsByUser(userId, howManyItems, filterInterestTags, pageViewList);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OpenHubProjectEntity> recommendViewedProjectsByItem(
			Long itemId, 
			Integer howManyItems) {
		final List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList = getProjectDetailPageViewRepository().findAllProjectDetailViews();
		return recommendViewedProjectsByItem(itemId, howManyItems, pageViewList);
	}
	
	@Transactional(readOnly = true)
	protected abstract List<OpenHubProjectEntity> recommendViewedProjectsByItem(
			Long itemId,
			Integer howManyItems,			
			List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList);

	@Transactional(readOnly = true)
	protected abstract List<OpenHubProjectEntity> recommendViewedProjectsByUser(
			Long userId,
			Integer howManyItems,
			boolean filterInterestTags,
			List<ImmutablePair<UserEntity, OpenHubProjectEntity>> pageViewList);

	@Override
	@Transactional(readOnly = true)
	public List<OpenHubProjectEntity> recommendRatingProjectsByUser(Long userId, Integer howManyItems) {
		return recommendRatingProjectsByUser(userId,howManyItems,false);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OpenHubProjectEntity> recommendRatingProjectsByUser(Long userId, Integer howManyItems, boolean filterInterestTags) {
		
		final Map<Long, Map<ImmutablePair<Long, Long>, Double>> ratingsMap =
				findAllRatings();
		
		return recommendRatingProjectsByUser(userId, howManyItems, filterInterestTags, ratingsMap);
	}

	@Transactional(readOnly = true)
	protected abstract List<OpenHubProjectEntity> recommendRatingProjectsByUser(
			Long userId,
			Integer howManyItems,
			boolean filterInterestTags,
			Map<Long, Map<ImmutablePair<Long, Long>, Double>> ratingsMap);

}
