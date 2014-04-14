package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.RoleEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.TagService;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name="accountMB")
@ViewScoped
@URLMappings(mappings={
	@URLMapping(
			id="accountMapping",
			beanName="accountMB", 
			pattern="/account/#{ /[0-9]+/ accountId}/",
			viewId="/account/account.jsf"),
	
	@URLMapping(
			id="newAccountMapping",
			beanName="accountMB", 
			pattern="/account/new",
			viewId="/account/newAccount.jsf")
})
public class AccountManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4801276509767530348L;

	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{tagService}")
	private TagService tagService;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	@ManagedProperty("#{standardPasswordEncoder}")
	private PasswordEncoder passwordEncoder;
	
	private UserEntity account;
	
	private boolean loggedUserOnly;;
	
	public AccountManagedBean() {
		this.account = new UserEntity();
	}
	
	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	
	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService TagService) {
		this.tagService = TagService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
	
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserEntity getAccount() {
		return account;
	}
	
	public void setAccount(UserEntity account) {
		this.account = account;
	}
	
	public boolean isLoggedUserOnly() {
		return loggedUserOnly;
	}

	public void setLoggedUserOnly(boolean loggedUserOnly) {
		this.loggedUserOnly = loggedUserOnly;
	}

	public void init(ComponentSystemEvent event) {
		if (this.account != null && this.account.getId() != null) {
			this.account = getUserService().findById(account.getId());
			
			Collections.sort(account.getInterestTags());
			
			if (isLoggedUserOnly()) {
				validateLoggedUser();
			}
		}
	}
	
	protected void validateLoggedUser() {
		UserEntity loggedUser = getUserDetailsService().loadFullLoggedUser();
		if (loggedUser != null && ! getAccount().getId().equals(loggedUser.getId())) {
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = ResourceBundle.getBundle("br.ufba.dcc.mestrado.computacao.account");
			
			String summary = bundle.getString("account.edit.settings.loggedUser.summary");
			String detail = bundle.getString("account.edit.settings.loggedUser.detail");
			FacesMessage facesMessage = new FacesMessage(summary, detail);
			
			context.addMessage(null, facesMessage);
		} 
	}
	
	
	public String getEncodedEmail() {
		String encoded = "";
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			
			if (account != null && ! StringUtils.isEmpty(account.getEmail())) {
				byte[] bytes = account.getEmail().getBytes("CP1252");
				encoded = Hex.encodeHexString(messageDigest.digest(bytes));
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return encoded;
	}
	
	public String saveAccount() {
		
		try {
			getUserService().save(getAccount());
			return "/account/accountSettings.jsf?faces-redirect=true&includeViewParams=true&accountId=" + getAccount().getId();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String createAccount() {
		
		String encodedPassword = getPasswordEncoder().encode(getAccount().getPassword());
		getAccount().setPassword(encodedPassword);
		
		List<RoleEnum> roleList = new ArrayList<>();
		roleList.add(RoleEnum.ROLE_USER);
		
		getAccount().setRoleList(roleList);
		
		
		
		try {
			getUserService().save(getAccount());			
			setAccount(new UserEntity());
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta criada com sucesso", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Ocorreu um erro durante esta operação", e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
			return "";
		}
		
		
		return "/login/login.jsf";
	}
	
	
	
}
