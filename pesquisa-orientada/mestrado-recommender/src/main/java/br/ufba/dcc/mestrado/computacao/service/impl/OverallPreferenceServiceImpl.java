package br.ufba.dcc.mestrado.computacao.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntryEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.BaseRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OverallPreferenceRepository;
import br.ufba.dcc.mestrado.computacao.service.base.CriteriumPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;

@Service(OverallPreferenceServiceImpl.BEAN_NAME)
public class OverallPreferenceServiceImpl extends BaseOhLohServiceImpl<Long, PreferenceEntity> implements OverallPreferenceService {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 610456503666069514L;
	
	public static final String BEAN_NAME =  "overallPreferenceService";
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CriteriumPreferenceService criteriumPreferenceService;
	
	@Autowired
	private RecommenderCriteriumService recommenderCriteriumService;
	
	@Autowired
	public OverallPreferenceServiceImpl(@Qualifier("overallPreferenceRepository") OverallPreferenceRepository repository) {
		super(repository, PreferenceEntity.class);
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public CriteriumPreferenceService getCriteriumPreferenceService() {
		return criteriumPreferenceService;
	}
	
	public void setCriteriumPreferenceService(
			CriteriumPreferenceService criteriumPreferenceService) {
		this.criteriumPreferenceService = criteriumPreferenceService;
	}
	
	public RecommenderCriteriumService getRecommenderCriteriumService() {
		return recommenderCriteriumService;
	}
	
	public void setRecommenderCriteriumService(
			RecommenderCriteriumService recommenderCriteriumService) {
		this.recommenderCriteriumService = recommenderCriteriumService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long countAllLast() {
		return ((OverallPreferenceRepository) getRepository()).countAllLast();
	}

	@Override
	@Transactional(readOnly = true)
	public Long countAllLastByProject(Long projectId) {
		return ((OverallPreferenceRepository) getRepository()).countAllLastByProject(projectId);
	}
	
	@Override
	public Long countAllLastReviewsByProject(Long projectId) {
		return ((OverallPreferenceRepository) getRepository()).countAllLastReviewsByProject(projectId);
	}
	
	@Override
	public Long countAllLastReviewsByUser(Long userId) {
		return ((OverallPreferenceRepository) getRepository()).countAllLastReviewsByUser(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long countAllByProjectAndUser(Long projectId, Long userId) {
		return ((OverallPreferenceRepository) getRepository()).countAllByProjectAndUser(projectId, userId);
	}

	@Override
	@Transactional(readOnly = true)
	public PreferenceEntity averagePreferenceByProject(Long projectId) {		
		PreferenceEntity averagePreference = new PreferenceEntity();
		
		//calculando valores médios de preferência
		Double averageValue = ((OverallPreferenceRepository) getRepository()).averagePreferenceByProject(projectId);
		averagePreference.setValue(averageValue == null ? 0 : averageValue);
		
		Map<RecommenderCriteriumEntity, Double> averageByCriterium = 
				getCriteriumPreferenceService().averagePreferenceByProject(projectId);
		
		List<PreferenceEntryEntity> preferenceEntryList = new ArrayList<>();
		
		if (! averageByCriterium.isEmpty()) {
			for (Map.Entry<RecommenderCriteriumEntity, Double> entry : averageByCriterium.entrySet()) {
				PreferenceEntryEntity preferenceEntry = new PreferenceEntryEntity();
				preferenceEntry.setPreference(averagePreference);
				preferenceEntry.setCriterium(entry.getKey());
				preferenceEntry.setValue(entry.getValue() == null ? 0 : entry.getValue());
				
				preferenceEntryList.add(preferenceEntry);
			}
		} else {
			List<RecommenderCriteriumEntity> criteriumList = getRecommenderCriteriumService().findAll();
			if (criteriumList != null && ! criteriumList.isEmpty()) {
				for (RecommenderCriteriumEntity criterium : criteriumList) {
					PreferenceEntryEntity preferenceEntry = new PreferenceEntryEntity();
					preferenceEntry.setPreference(averagePreference);
					preferenceEntry.setCriterium(criterium);
					preferenceEntry.setValue(Double.valueOf(0));
					
					preferenceEntryList.add(preferenceEntry);
				}
			}
		}
		
		
		Comparator<PreferenceEntryEntity> entryComparator = new Comparator<PreferenceEntryEntity>() {
			@Override
			public int compare(PreferenceEntryEntity o1, PreferenceEntryEntity o2) {
				return o1.getCriterium().getName().compareTo(o2.getCriterium().getName());
			}
		};
		
		Collections.sort(preferenceEntryList, entryComparator);
		
		averagePreference.setPreferenceEntryList(preferenceEntryList);
		
		return averagePreference;		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PreferenceEntity> findAllLastByProject(Long projectId) {
		return ((OverallPreferenceRepository) getRepository()).findAllLastByProject(projectId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PreferenceEntity> findAllLastByProject(Long projectId, Integer startAt, Integer offset) {
		return ((OverallPreferenceRepository) getRepository()).findAllLastByProject(projectId, startAt, offset);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PreferenceEntity> findAllLastReviewsByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		return ((OverallPreferenceRepository) getRepository()).findAllLastReviewsByProject(projectId, startAt, offset, orderByRegisteredAt, orderByReviewRanking);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<PreferenceEntity> findAllLastReviewsByUser(
			Long userId, 
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		return ((OverallPreferenceRepository) getRepository()).findAllLastReviewsByUser(userId, startAt, offset, orderByRegisteredAt, orderByReviewRanking);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PreferenceEntity> findAllByProjectAndUser(Long projectId, Long userId) {
		return ((OverallPreferenceRepository) getRepository()).findAllByProjectAndUser(projectId, userId);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public PreferenceEntity findLastByProjectAndUser(Long projectId, Long userId) {
		return ((OverallPreferenceRepository) getRepository()).findLastByProjectAndUser(projectId, userId);
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
}
