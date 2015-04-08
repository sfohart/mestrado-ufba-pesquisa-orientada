
package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name="accountSettingsMB")
@ViewScoped
@URLMappings(mappings={
	@URLMapping(
			id="accountSettingsMapping",
			beanName="accountSettingsMB", 
			pattern="/account/#{ /[0-9]+/ accountId}/settings/",
			viewId="/account/accountSettings.jsf")
})
public class AccountSettingsManagedBean extends AccountManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7912397294500738933L;
	
	public AccountSettingsManagedBean() {
		super();
		setLoggedUserOnly(true);
	}
	
	public void initViewSettings (ComponentSystemEvent event) {
		init(event);
		
		if (getAccount() != null && getAccount().getId() != null) {
			UserEntity loggedUser = getUserDetailsService().loadFullLoggedUser();
			if (loggedUser != null && ! getAccount().getId().equals(loggedUser.getId())) {
				FacesContext context = FacesContext.getCurrentInstance();
				ResourceBundle bundle = ResourceBundle.getBundle("br.ufba.dcc.mestrado.computacao.account");
				
				String summary = bundle.getString("account.view.settings.loggedUser.summary");
				String detail = bundle.getString("account.view.settings.loggedUser.detail");
				FacesMessage facesMessage = new FacesMessage(summary, detail);
				
				context.addMessage(null, facesMessage);
			} 
		}
	}


}

