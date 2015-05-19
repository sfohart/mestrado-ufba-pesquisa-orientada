package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceReviewEntity;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name="accountReviewedProjectsMB")
@ViewScoped
@URLMappings(mappings={
		@URLMapping(
				id="accountReviewedProjectsMapping",
				beanName="accountReviewedProjectsMB", 
				pattern="/account/#{ /[0-9]+/ accountId}/reviewedProjects",
				viewId="/account/accountReviewedProjects.jsf")
	})
public class AccountReviewedProjectsManagedBean extends AbstractAccountDataListManagedBean<Long, PreferenceReviewEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3050562489611789733L;

	@Override
	protected List<PreferenceReviewEntity> searchDataList(
			Integer startPosition, Integer offset) {
		return getPreferenceReviewService().findAllLastReviewsByUser(getAccount().getId(), startPosition, offset, Boolean.TRUE, Boolean.TRUE);
	}

	@Override
	protected Long countDataList() {
		return getPreferenceReviewService().countAllLastReviewsByUser(getAccount().getId());
	}
}
