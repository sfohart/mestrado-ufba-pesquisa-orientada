package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name="accountRatedProjectsMB")
@ViewScoped
@URLMappings(mappings={
		@URLMapping(
				id="accountRatedProjectsMapping",
				beanName="accountRatedProjectsMB", 
				pattern="/account/#{ /[0-9]+/ accountId}/ratedProjects",
				viewId="/account/accountRatedProjects.jsf")
	})
public class AccountRatedProjectsManagedBean extends AbstractAccountDataListManagedBean<Long, PreferenceEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6594352167721079964L;
	
	@Override
	protected List<PreferenceEntity> searchDataList(Integer startPosition, Integer offset) {
		return getOverallRatingService().findAllLastPreferenceByUser(getAccount().getId(), startPosition, offset, Boolean.TRUE,Boolean.TRUE);
	}

	@Override
	protected Long countDataList() {
		return getOverallRatingService().countAllLastPreferenceByUser(getAccount().getId());
	}
	
}
