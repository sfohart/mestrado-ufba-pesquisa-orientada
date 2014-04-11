package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.model.BooleanPreference;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewInfo;
import br.ufba.dcc.mestrado.computacao.repository.base.ProjectDetailPageViewRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.ProjectDetailPageViewRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderService;
import br.ufba.dcc.mestrado.computacao.service.basic.ProjectDetailPageViewService;

@Service(ProjectDetailPageViewServiceImpl.BEAN_NAME)
public class ProjectDetailPageViewServiceImpl
		extends  BaseServiceImpl<Long, ProjectDetailPageViewEntity>
		implements ProjectDetailPageViewService {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5717255957053110654L;
	
	public static final String BEAN_NAME =  "projectDetailPageViewService";
	
	@Autowired
	private RecommenderService recommenderService;
	
	
	@Autowired
	public ProjectDetailPageViewServiceImpl(
			@Qualifier(ProjectDetailPageViewRepositoryImpl.BEAN_NAME) ProjectDetailPageViewRepository repository) {
		super(repository, ProjectDetailPageViewEntity.class);
	}
	
	public List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo() {
		return ((ProjectDetailPageViewRepository) getRepository()).findAllProjectDetailPageViewInfo();
	}
	
	public List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo(
			Integer startAt, 
			Integer offset) {
		return ((ProjectDetailPageViewRepository) getRepository()).findAllProjectDetailPageViewInfo(startAt, offset);
	}
	
	public List<OhLohProjectEntity> findAllProjectRecentlyViewed(
			UserEntity user,
			Integer startAt, 
			Integer offset) {
		return ((ProjectDetailPageViewRepository) getRepository()).findAllProjectRecentlyViewed(user, startAt, offset);
	}
	
	@Override
	public GenericBooleanPrefItemBasedRecommender buildProjectRecommender() throws TasteException {
		GenericBooleanPrefItemBasedRecommender recommender = null;
		
		List<BooleanPreference> preferenceList = ((ProjectDetailPageViewRepository) getRepository()).findAllPageViewDataWithUsers();
		
		GenericBooleanPrefDataModel dataModel = recommenderService.buildBooleanDataModel(preferenceList);
		RecommenderBuilder recommenderBuilder = recommenderService.createBooleanItemBasedRecomenderBuilder(dataModel);
		
		recommender = (GenericBooleanPrefItemBasedRecommender) recommenderBuilder.buildRecommender(dataModel);
		
		return recommender;
	}
	
}
