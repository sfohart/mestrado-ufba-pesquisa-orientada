package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name = "changePasswordMB")
@ViewScoped
@URLMappings(
		mappings = { 
				@URLMapping(
						id = "changePasswordMapping", 
						beanName = "changePasswordMB", 
						pattern = "/account/#{ /[0-9]+/ accountId}/changePassword", 
						viewId = "/account/accountChangePassword.jsf") 
		})
public class AccountChangePasswordManagedBean extends AccountManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -367672531825519484L;

	private String oldPassword;
	private String newPassword;
	private String confirmNewPassword;

	private UIComponent oldPasswordInput;

	public AccountChangePasswordManagedBean() {
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

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public String saveAccount() {

		if (newPassword != null && !"".equals(newPassword)) {
			String encodedNewPassword = getPasswordEncoder()
					.encode(newPassword);
			getAccount().setPassword(encodedNewPassword);

			return super.saveAccount();
		}

		return null;
	}

}
