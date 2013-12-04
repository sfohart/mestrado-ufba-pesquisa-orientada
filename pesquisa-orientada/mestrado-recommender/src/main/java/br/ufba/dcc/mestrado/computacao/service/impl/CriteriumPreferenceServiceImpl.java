package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.model.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.CriteriumPreferenceRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.CriteriumPreferenceRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.CriteriumPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderCriteriumService;

@Service(CriteriumPreferenceServiceImpl.BEAN_NAME)
public class CriteriumPreferenceServiceImpl extends BaseOhLohServiceImpl<Long, PreferenceEntity> implements CriteriumPreferenceService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7374017647359060240L;
	
	public static final String BEAN_NAME = "criteriumPreferenceService";
	
	@Autowired
	private RecommenderCriteriumService recommenderCriteriumService;
	
	@Autowired
	public CriteriumPreferenceServiceImpl(@Qualifier(CriteriumPreferenceRepositoryImpl.BEAN_NAME) CriteriumPreferenceRepository repository) {
		super(repository, PreferenceEntity.class);
	}
	
	public RecommenderCriteriumService getRecommenderCriteriumService() {
		return this.recommenderCriteriumService;
	}
	
	public void setRecommenderCriteriumService(RecommenderCriteriumService recommenderCriteriumService) {
		this.recommenderCriteriumService = recommenderCriteriumService;
	}

	@Override
	public Long countAllByCriterium(Long criteriumId) {
		return ((CriteriumPreferenceRepository) getRepository()).countAllByCriterium(criteriumId);
	}

	@Override
	public Double averagePreferenceByProjectAndCriterium(Long projectId, Long criteriumId) {
		return ((CriteriumPreferenceRepository) getRepository()).averagePreferenceByProjectAndCriterium(projectId, criteriumId);
	}
	
	public Map<RecommenderCriteriumEntity, Double> averagePreferenceByProject(Long projectId) {
		Map<RecommenderCriteriumEntity, Double> result = new HashMap<RecommenderCriteriumEntity, Double>();
		
		List<RecommenderCriteriumEntity> criteriumList = getRecommenderCriteriumService().findAll();
		if (criteriumList != null && ! criteriumList.isEmpty()) {
			for (RecommenderCriteriumEntity criterium : criteriumList) {
				Double average = averagePreferenceByProjectAndCriterium(projectId, criterium.getId());
				result.put(criterium, average);
			}
		}
		
		
		return result;
	}

	@Override
	public List<Preference> findAllByCriterium(Long criteriumID) {
		return ((CriteriumPreferenceRepository) getRepository()).findAllByCriterium(criteriumID);
	}

	@Override
	public List<Preference> findAllByCriterium(
			Long criteriumID, 
			Integer limit,
			Integer offset) {

		return ((CriteriumPreferenceRepository) getRepository()).findAllByCriterium(criteriumID, limit, offset);
		
	}
	
}
