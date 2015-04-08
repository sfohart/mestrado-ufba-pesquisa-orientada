
package br.ufba.dcc.mestrado.computacao.service.core.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntryEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.BaseRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OverallRatingRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.OverallRatingRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.impl.BaseServiceImpl;

@Service(OverallRatingServiceImpl.BEAN_NAME)
public class OverallRatingServiceImpl
		extends BaseServiceImpl<Long, PreferenceEntity>
		implements OverallRatingService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6708484167153620441L;

	public static final String BEAN_NAME =  "overallRatingService";
	
	@Autowired
	private RecommenderCriteriumService recommenderCriteriumService;
	
	@Autowired
	private RatingByCriteriumService ratingByCriteriumService;
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	public OverallRatingServiceImpl(@Qualifier(OverallRatingRepositoryImpl.BEAN_NAME) OverallRatingRepository repository) {
		super(repository, PreferenceEntity.class);
	}
	
	public RecommenderCriteriumService getRecommenderCriteriumService() {
		return recommenderCriteriumService;
	}

	public void setRecommenderCriteriumService(
			RecommenderCriteriumService recommenderCriteriumService) {
		this.recommenderCriteriumService = recommenderCriteriumService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public RatingByCriteriumService getRatingByCriteriumService() {
		return ratingByCriteriumService;
	}

	public void setRatingByCriteriumService(
			RatingByCriteriumService ratingByCriteriumService) {
		this.ratingByCriteriumService = ratingByCriteriumService;
	}

	@Override
	@Transactional(readOnly = true)
	public Double averageRatingByItem(Long itemId) {
		return ((OverallRatingRepository) getRepository()).averageRatingByItem(itemId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PreferenceEntity averagePreferenceByItem(Long itemId) {
		List<RecommenderCriteriumEntity> criteriaList = getRecommenderCriteriumService().findAll();
		
		OpenHubProjectEntity project = getProjectService().findById(itemId);
		Double averageOverallRating = averageRatingByItem(itemId);
		
		PreferenceEntity averagePreference = new PreferenceEntity();
		averagePreference.setProject(project);
		averagePreference.setValue(averageOverallRating);
		
		if (criteriaList != null && ! criteriaList.isEmpty()) {
			
			averagePreference.setPreferenceEntryList(new ArrayList<PreferenceEntryEntity>());
			
			for (RecommenderCriteriumEntity criterium : criteriaList) {
				PreferenceEntryEntity averagePreferenceEntry = new PreferenceEntryEntity();
				averagePreferenceEntry.setCriterium(criterium);
				
				Double averageRatingByCriterium = getRatingByCriteriumService().averagePreferenceByItemAndCriterium(itemId, criterium.getId());
				averagePreferenceEntry.setValue(averageRatingByCriterium);
				averagePreferenceEntry.setPreference(averagePreference);
				
				averagePreference.getPreferenceEntryList().add(averagePreferenceEntry);
			}
		}
		
		return averagePreference;
	}
	

	@Override
	@Transactional(readOnly = true)
	public Map<ImmutablePair<Long, Long>, Double> findAllLastOverallPreferenceValue() {
		return ((OverallRatingRepository) getRepository()).findAllLastOverallPreferenceValue();
	}

	@Override
	@Transactional(readOnly = true)
	public Map<ImmutablePair<Long, Long>, Double> findAllLastOverallPreferenceValueByItem(
			Long itemId) {
		return ((OverallRatingRepository) getRepository()).findAllLastOverallPreferenceValueByItem(itemId);
	}
	
	

	@Override
	@Transactional(readOnly = true)
	public List<PreferenceEntity> findAllLastPreferenceByProject(
			Long projectId,
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		return ((OverallRatingRepository) getRepository()).findAllLastPreferenceByProject(projectId, startAt, offset, orderByRegisteredAt, orderByReviewRanking);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PreferenceEntity> findAllLastPreferenceByUser(
			Long userId,
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		return ((OverallRatingRepository) getRepository()).findAllLastPreferenceByUser(userId, startAt, offset, orderByRegisteredAt, orderByReviewRanking);
	}
	
	

	@Override
	@Transactional(readOnly = true)
	public Long countAllLast() {
		return ((OverallRatingRepository) getRepository()).countAllLast();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long countAllLastPreferenceByProject(Long projectId) {
		return ((OverallRatingRepository) getRepository()).countAllLastPreferenceByProject(projectId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long countAllLastPreferenceByUser(Long userId) {
		return ((OverallRatingRepository) getRepository()).countAllLastPreferenceByUser(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ImmutablePair<OpenHubProjectEntity, Long>> findRatingCountByProject(
			Integer startAt, 
			Integer offset) {
		return ((OverallRatingRepository) getRepository()).findRatingCountByProject(startAt, offset);
	}
	
	/**
	 * @see BaseRepository
	 */
	@Override
	@Transactional
	public PreferenceEntity save(PreferenceEntity entity) throws Exception {
		validateEntity(entity);
		return super.save(entity);
	}

	/**
	 * 
	 * @param entity
	 */
	private void validateEntity(PreferenceEntity entity) {
		
		if (entity.getRegisteredAt() == null) {
			entity.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
		}
		
		//validar apropriadamente os valores
		if (entity.getPreferenceEntryList() != null && ! entity.getPreferenceEntryList().isEmpty()) {
			for (PreferenceEntryEntity entry : entity.getPreferenceEntryList()) {
				if (entry.getValue() == null || entry.getValue() == 0) {
					entry.setNotAvailable(true);
				}
			}
		}
		
		if (entity.getPreferenceReview() != null) {
			
			if  (entity.getPreferenceReview().getId() == null) {
				//se a review não tem titulo nem descrição, pra que mantê-la?
				if (StringUtils.isEmpty(entity.getPreferenceReview().getDescription()) 
						&& StringUtils.isEmpty(entity.getPreferenceReview().getTitle())) {
					entity.setPreferenceReview(null);
				}	
			} else {
				//atualizar contadores de utilidade e inutilidade
				Long usefulCount = 0L;
				Long uselessCount = 0L;
				
				if (entity.getPreferenceReview().getUsefulList() != null) {
					usefulCount = Long.valueOf(entity.getPreferenceReview().getUsefulList().size());
					entity.getPreferenceReview().setUsefulCount(usefulCount);
				}
				
				if (entity.getPreferenceReview().getUselessList() != null) {
					uselessCount = Long.valueOf(entity.getPreferenceReview().getUselessList().size());
					entity.getPreferenceReview().setUselessCount(uselessCount);
				}
				
				if (usefulCount > 0 || uselessCount > 0) {
					Long total = usefulCount + uselessCount;
					entity.getPreferenceReview().setReviewRanking(Double.valueOf(usefulCount / total));
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PreferenceEntity findLastOverallPreferenceByUserAndItem(
			Long userId,
			Long itemId) {
		return ((OverallRatingRepository) getRepository()).findLastOverallPreferenceByUserAndItem(userId,itemId);
	}

}

