package br.ufba.dcc.mestrado.computacao.service.basic;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewInfo;
import br.ufba.dcc.mestrado.computacao.service.base.BaseOhLohService;

public interface ProjectDetailPageViewService extends BaseOhLohService<Long, ProjectDetailPageViewEntity>{

	List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo();
	List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo(Integer startAt, Integer offset);
	
}
