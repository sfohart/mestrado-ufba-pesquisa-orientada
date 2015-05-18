package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;

@ManagedBean(name="accountRatedProjectsMB")
@ViewScoped
@URLMappings(mappings={
		@URLMapping(
				id="accountRatedProjectsMapping",
				beanName="accountRatedProjectsMB", 
				pattern="/account/#{ /[0-9]+/ accountId}/ratedProjects",
				viewId="/account/accountRatedProjects.jsf")
	})
public class AccountRatedProjectsManagedBean extends AbstractAccountManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6594352167721079964L;
	
	private List<PreferenceEntity> ratedProjects;

	public void init(ComponentSystemEvent event) {
		super.init(event);
		
		if (getAccount() != null && getAccount().getId() != null) {
			loadRatedProjects();
		}
	}

	private void loadRatedProjects() {
		this.ratedProjects = getOverallRatingService().findAllLastPreferenceByUser(getAccount().getId(), null, null, Boolean.TRUE,Boolean.TRUE);
	}
	
	public List<PreferenceEntity> getRatedProjects() {
		return ratedProjects;
	}
	
}
