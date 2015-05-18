package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;

@ManagedBean(name="accountViewedProjectsMB")
@ViewScoped
@URLMappings(mappings={
		@URLMapping(
				id="accountViewedProjectsMapping",
				beanName="accountViewedProjectsMB", 
				pattern="/account/#{ /[0-9]+/ accountId}/viewedProjects",
				viewId="/account/accountViewedProjects.jsf")
	})
public class AccountViewedProjectsManagedBean extends AbstractAccountManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3129797625819667020L;
	
	private List<OpenHubProjectEntity> viewedProjects;
	
	public void init(ComponentSystemEvent event) {
		super.init(event);
		
		if (getAccount() != null && getAccount().getId() != null) {
			loadViewedProjects();
		}
	}

	private void loadViewedProjects() {
		this.viewedProjects = getPageViewService().findAllProjectRecentlyViewedByUser(getAccount());
	}

	public List<OpenHubProjectEntity> getViewedProjects() {
		return viewedProjects;
	}
	
}
