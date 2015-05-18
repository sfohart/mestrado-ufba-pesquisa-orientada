package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceReviewEntity;

@ManagedBean(name="accountReviewedProjectsMB")
@ViewScoped
@URLMappings(mappings={
		@URLMapping(
				id="accountReviewedProjectsMapping",
				beanName="accountReviewedProjectsMB", 
				pattern="/account/#{ /[0-9]+/ accountId}/reviewedProjects",
				viewId="/account/accountReviewedProjects.jsf")
	})
public class AccountReviewedProjectsManagedBean extends AbstractAccountManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8148862573445451850L;
	
	private List<PreferenceReviewEntity> reviewedProjects;

	public void init(ComponentSystemEvent event) {
		super.init(event);
		
		if (getAccount() != null && getAccount().getId() != null) {
			loadReviewedProjects();
		}
	}

	private void loadReviewedProjects() {
		this.reviewedProjects = getPreferenceReviewService().findAllLastReviewsByUser(getAccount().getId(), null, null, Boolean.TRUE, Boolean.FALSE);
	}
	
	public List<PreferenceReviewEntity> getReviewedProjects() {
		return reviewedProjects;
	}
}
