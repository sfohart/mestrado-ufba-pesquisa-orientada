package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubTagEntity;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name="accountInterestTagsMB")
@ViewScoped
@URLMappings(mappings={
	@URLMapping(
			id="accountInterestTagsMapping",
			beanName="accountInterestTagsMB", 
			pattern="/account/#{ /[0-9]+/ accountId}/interestTags",
			viewId="/account/accountInterestTags.jsf"),
})
public class AccountInterestTagsManagedBean extends AccountManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2434076393067892352L;
	
	private String selectedInterestTags;
	
	public AccountInterestTagsManagedBean() {
		super();
		setLoggedUserOnly(true);
	}
	
	public String getSelectedInterestTags() {
		return selectedInterestTags;
	}

	public void setSelectedInterestTags(String selectedInterestTags) {
		this.selectedInterestTags = selectedInterestTags;
	}
	
	public void addInterestTags(ActionEvent event) {
		Set<OpenHubTagEntity> allTags = new HashSet<>();
		allTags.addAll(getAccount().getInterestTags());
		
		if (selectedInterestTags != null && ! "".equals(selectedInterestTags)) {
			String[] arraySelectedTags = selectedInterestTags.split(",");
			for (String selectedTag : arraySelectedTags) {
				OpenHubTagEntity tag = getTagService().findByName(selectedTag);
				allTags.add(tag);
			}
		}
		
		getAccount().getInterestTags().clear();
		getAccount().getInterestTags().addAll(allTags);
		
		Collections.sort(getAccount().getInterestTags());
		
		try {
			getUserService().save(getAccount());
			clearSelectedTags();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void clearSelectedTags() {
		this.selectedInterestTags = null;
	}
	
	public void removeInterestTags(ActionEvent event) {
		OpenHubTagEntity tag = (OpenHubTagEntity)
				event.getComponent().getAttributes().get("tag");
		
		if (getAccount().getInterestTags() != null) {
			if (getAccount().getInterestTags().contains(tag)) {
				getAccount().getInterestTags().remove(tag);
			}
		}
		
		try {
			getUserService().save(getAccount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
