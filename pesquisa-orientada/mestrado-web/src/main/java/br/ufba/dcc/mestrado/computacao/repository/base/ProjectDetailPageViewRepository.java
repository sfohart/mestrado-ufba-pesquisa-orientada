package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewInfo;

public interface ProjectDetailPageViewRepository extends BaseRepository<Long, ProjectDetailPageViewEntity>{
	
	List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo();
	List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo(Integer startAt, Integer offset);
	
}
