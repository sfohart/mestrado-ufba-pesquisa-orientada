package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import org.apache.mahout.cf.taste.impl.model.BooleanPreference;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewInfo;

public interface ProjectDetailPageViewRepository extends BaseRepository<Long, ProjectDetailPageViewEntity>{
	
	List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo();
	List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo(Integer startAt, Integer offset);
	
	
	List<OhLohProjectEntity> findAllProjectRecentlyViewed(
			UserEntity user,
			String ipAddress,
			Integer startAt, 
			Integer offset);
	
	
	List<BooleanPreference> findAllPageViewDataWithUsers();
	
}
