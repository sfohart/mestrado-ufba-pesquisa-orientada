
package br.ufba.dcc.mestrado.computacao.service.core.impl;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.ProjectDetailPageViewRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.ProjectDetailPageViewRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.impl.BaseServiceImpl;

@Service(ProjectDetailPageViewServiceImpl.BEAN_NAME)
public class ProjectDetailPageViewServiceImpl
		extends BaseServiceImpl<Long, ProjectDetailPageViewEntity>
		implements ProjectDetailPageViewService {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6804788517628599498L;
	
	public static final String BEAN_NAME =  "projectDetailPageViewService";
	
	
	@Autowired
	public ProjectDetailPageViewServiceImpl(
			@Qualifier(ProjectDetailPageViewRepositoryImpl.BEAN_NAME) ProjectDetailPageViewRepository repository) {
		super(repository, ProjectDetailPageViewEntity.class);
	}


	@Override
	public List<ImmutablePair<UserEntity, OpenHubProjectEntity>> findAllProjectDetailViews() {
		return ((ProjectDetailPageViewRepository) getRepository()).findAllProjectDetailViews();
	}
	
	@Override
	public List<ImmutablePair<UserEntity, OpenHubProjectEntity>> findAllProjectDetailViews(Integer startAt, Integer offset) {
		return ((ProjectDetailPageViewRepository) getRepository()).findAllProjectDetailViews(startAt, offset);
	}


	@Override
	public List<OpenHubProjectEntity> findAllProjectRecentlyViewedByUser(UserEntity user) {
		return findAllProjectRecentlyViewedByUser(user, null, null);
	}
			
	
	@Override
	public List<OpenHubProjectEntity> findAllProjectRecentlyViewedByUser(
			UserEntity user, 
			Integer startAt, 
			Integer offset) {
		
		return ((ProjectDetailPageViewRepository) getRepository())
				.findAllProjectRecentlyViewedByUser(user, startAt, offset);
	}


	@Override
	public List<ImmutablePair<OpenHubProjectEntity, Long>> groupProjectDetailViewsCount() {
		return ((ProjectDetailPageViewRepository) getRepository()).groupProjectDetailViewsCount();
	}


	@Override
	public List<ImmutablePair<OpenHubProjectEntity, Long>> groupProjectDetailViewsCount(Integer startAt, Integer offset) {
		return ((ProjectDetailPageViewRepository) getRepository()).groupProjectDetailViewsCount(startAt, offset);
	}

	
}

