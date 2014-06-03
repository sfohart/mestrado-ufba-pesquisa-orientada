package br.ufba.dcc.mestrado.computacao.dto.pageview;

import java.io.Serializable;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;

public class ProjectDetailPageViewInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 377951959478741225L;

	private OhLohProjectEntity project;
	private Long pageViewCount;

	public OhLohProjectEntity getProject() {
		return project;
	}

	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}

	public Long getPageViewCount() {
		return pageViewCount;
	}

	public void setPageViewCount(Long pageViewCount) {
		this.pageViewCount = pageViewCount;
	}

}
