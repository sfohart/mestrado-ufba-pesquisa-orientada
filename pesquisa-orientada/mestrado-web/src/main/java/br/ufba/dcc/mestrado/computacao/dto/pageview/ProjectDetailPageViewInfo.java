
package br.ufba.dcc.mestrado.computacao.dto.pageview;

import java.io.Serializable;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;

public class ProjectDetailPageViewInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 377951959478741225L;

	private OpenHubProjectEntity project;
	private Long pageViewCount;

	public OpenHubProjectEntity getProject() {
		return project;
	}

	public void setProject(OpenHubProjectEntity project) {
		this.project = project;
	}

	public Long getPageViewCount() {
		return pageViewCount;
	}

	public void setPageViewCount(Long pageViewCount) {
		this.pageViewCount = pageViewCount;
	}

}

