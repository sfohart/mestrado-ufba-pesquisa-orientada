package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.security.crypto.password.PasswordEncoder;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name = "changePasswordMB")
@ViewScoped
@URLMappings(mappings = { 
		@URLMapping(
				id = "changePasswordMapping", 
				beanName = "changePasswordMB", 
				pattern = "/account/#{ /[0-9]+/ accountId}/changePassword", 
				viewId = "/account/changePassword.jsf") 
})
public class ChangePasswordManagedBean extends AccountManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -367672531825519484L;
	
	private String oldPassword;
	private String newPassword;
	
	private UIComponent oldPasswordInput;
	
	
	public ChangePasswordManagedBean() {
		super();
		setLoggedUserOnly(true);
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public UIComponent getOldPasswordInput() {
		return oldPasswordInput;
	}

	public void setOldPasswordInput(UIComponent oldPasswordInput) {
		this.oldPasswordInput = oldPasswordInput;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String saveAccount() {
		
		if (oldPassword != null && ! "".equals(oldPassword)) {
			String encodedOldPassword = getPasswordEncoder().encode(oldPassword);
			if (getAccount().getPassword().equals(encodedOldPassword)) {
				
				String encodedNewPassword = getPasswordEncoder().encode(newPassword);
				getAccount().setPassword(encodedNewPassword);
				
				return saveAccount();
			} 
		}

		return null;
	}

}
