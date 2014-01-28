package br.ufba.dcc.mestrado.computacao.service.basic;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewInfo;
import br.ufba.dcc.mestrado.computacao.service.base.BaseOhLohService;

public interface ProjectDetailPageViewService extends BaseOhLohService<Long, ProjectDetailPageViewEntity>{

	List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo();
	List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo(Integer startAt, Integer offset);
	
	List<OhLohProjectEntity> findAllProjectRecentlyViewed(
			UserEntity user,
			String ipAddress,
			Integer startAt, 
			Integer offset);
	
	GenericBooleanPrefItemBasedRecommender buildProjectRecommender() throws TasteException;
	
}
