package br.ufba.dcc.mestrado.computacao.service.recommender.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.MahoutDataModelService;

public abstract class AbstractRecommenderServiceImpl {

	@Autowired
	protected ProjectService projectService;
	
	@Autowired
	protected MahoutDataModelService dataModelService;
	
	@Autowired
	protected RecommenderCriteriumService criteriumService;
	
	@Autowired
	protected RatingByCriteriumService ratingByCriteriumService;
	
	@Autowired
	private UserService userService;
	
	protected IDRescorer buildIdRescorer(Long userId) {
		
		final UserEntity currentUser = userService.findById(userId);
		
		IDRescorer idRescorer = new IDRescorer() {
			
			@Override
			public double rescore(long id, double originalScore) {
				return originalScore;
			}
			
			@Override
			public boolean isFiltered(long id) {
				OpenHubProjectEntity project = projectService.findById(id);
				if (currentUser.getInterestTags() != null && ! currentUser.getInterestTags().isEmpty()) {
					for (OpenHubTagEntity tag : currentUser.getInterestTags()) {
						if (project.getTags().contains(tag)) {
							return false;
						}
					}
					
					return true;
				} else {
					return false;
				}
			}
		};
		
		return idRescorer;
		
	}
	
	@Transactional(readOnly = true)
	public List<OpenHubProjectEntity> getRecommendedProjects(List<RecommendedItem> recommendedItems) {
		List<OpenHubProjectEntity> projectList = null;
		
		if (recommendedItems != null) {
			projectList = new LinkedList<OpenHubProjectEntity>();
			
			for (RecommendedItem recommendedItem : recommendedItems) {
				OpenHubProjectEntity project = projectService.findById(recommendedItem.getItemID());
				projectList.add(project);
			}
		}
		
		return projectList;
	}
	
	
	protected Map<RecommenderCriteriumEntity, DataModel> buildDataModelMap() {
		List<RecommenderCriteriumEntity> criteria = criteriumService.findAll();
		Map<RecommenderCriteriumEntity, DataModel> dataModelMap = new HashMap<RecommenderCriteriumEntity, DataModel>();
		
		if (criteria != null) {
			
			for (RecommenderCriteriumEntity criterium : criteria) {
				Map<ImmutablePair<Long,Long>,Double> ratingsMap = ratingByCriteriumService.findAllLastPreferenceByCriterium(criterium.getId());
				
				List<Preference> preferenceList = new ArrayList<Preference>();
				for (Map.Entry<ImmutablePair<Long,Long>,Double> entry : ratingsMap.entrySet()) {
					
					Preference preference = new GenericPreference(
							entry.getKey().getLeft(),
							entry.getKey().getRight(),
							entry.getValue().floatValue()
							);
					
					preferenceList.add(preference);
				}
				
				
				DataModel dataModel = dataModelService.buildDataModelByUser(preferenceList);
				dataModelMap.put(criterium, dataModel);
			}
		}
		
		return dataModelMap;
	}

}
