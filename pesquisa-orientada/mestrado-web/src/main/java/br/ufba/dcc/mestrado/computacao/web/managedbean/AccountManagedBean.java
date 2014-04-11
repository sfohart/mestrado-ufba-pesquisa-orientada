package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;
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
			id="accountSettingsMapping",
			beanName="accountMB", 
			pattern="/account/#{ /[0-9]+/ accountId}/settings/",
			viewId="/account/accountSettings.jsf"),
	@URLMapping(
			id="accountBasicDataEditMapping",
			beanName="accountMB", 
			pattern="/account/#{ /[0-9]+/ accountId}/basicDataEdit",
			viewId="/account/accountBasicDataEdit.jsf"),
	@URLMapping(
			id="accountInterestTagsMapping",
			beanName="accountMB", 
			pattern="/account/#{ /[0-9]+/ accountId}/interestTags",
			viewId="/account/accountInterestTags.jsf"),
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
	
	@ManagedProperty("#{ohLohTagService}")
	private TagService tagService;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	@ManagedProperty("#{standardPasswordEncoder}")
	private PasswordEncoder passwordEncoder;
	
	private UserEntity account;
	
	private String selectedInterestTags;
	
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

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
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
	
	

	public String getSelectedInterestTags() {
		return selectedInterestTags;
	}

	public void setSelectedInterestTags(String selectedInterestTags) {
		this.selectedInterestTags = selectedInterestTags;
	}

	public void init(ComponentSystemEvent event) {
		if (this.account != null && this.account.getId() != null) {
			this.account = getUserService().findById(account.getId());
			
			Collections.sort(account.getInterestTags());
		}
	}
	
	public void initEditSettings (ComponentSystemEvent event) {
		init(event);		
		
		if (this.account != null && this.account.getId() != null) {
			UserEntity loggedUser = getUserDetailsService().loadFullLoggedUser();
			if (loggedUser != null && ! this.account.getId().equals(loggedUser.getId())) {
				FacesContext context = FacesContext.getCurrentInstance();
				ResourceBundle bundle = ResourceBundle.getBundle("br.ufba.dcc.mestrado.computacao.account");
				
				String summary = bundle.getString("account.edit.settings.loggedUser.summary");
				String detail = bundle.getString("account.edit.settings.loggedUser.detail");
				FacesMessage facesMessage = new FacesMessage(summary, detail);
				
				context.addMessage(null, facesMessage);				
			}
		}
	}
	
	public void initInterestTagSettings (ComponentSystemEvent event) {
		init(event);
		
		if (this.account != null && this.account.getId() != null) {
			UserEntity loggedUser = getUserDetailsService().loadFullLoggedUser();
			if (loggedUser != null && ! this.account.getId().equals(loggedUser.getId())) {
				FacesContext context = FacesContext.getCurrentInstance();
				ResourceBundle bundle = ResourceBundle.getBundle("br.ufba.dcc.mestrado.computacao.account");
				
				String summary = bundle.getString("account.edit.settings.loggedUser.summary");
				String detail = bundle.getString("account.edit.settings.loggedUser.detail");
				FacesMessage facesMessage = new FacesMessage(summary, detail);
				
				context.addMessage(null, facesMessage);
			} 
		}
	}
	
	public void initViewSettings (ComponentSystemEvent event) {
		init(event);
		
		if (this.account != null && this.account.getId() != null) {
			UserEntity loggedUser = getUserDetailsService().loadFullLoggedUser();
			if (loggedUser != null && ! this.account.getId().equals(loggedUser.getId())) {
				FacesContext context = FacesContext.getCurrentInstance();
				ResourceBundle bundle = ResourceBundle.getBundle("br.ufba.dcc.mestrado.computacao.account");
				
				String summary = bundle.getString("account.view.settings.loggedUser.summary");
				String detail = bundle.getString("account.view.settings.loggedUser.detail");
				FacesMessage facesMessage = new FacesMessage(summary, detail);
				
				context.addMessage(null, facesMessage);
			} 
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
			return "/account/accountSettings.jsf?faces-redirect=true&includeViewParams=true&accountId=" + account.getId();
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
	
	public void addInterestTags(ActionEvent event) {
		Set<OhLohTagEntity> allTags = new HashSet<>();
		allTags.addAll(getAccount().getInterestTags());
		
		if (selectedInterestTags != null && ! "".equals(selectedInterestTags)) {
			String[] arraySelectedTags = selectedInterestTags.split(",");
			for (String selectedTag : arraySelectedTags) {
				OhLohTagEntity tag = getTagService().findByName(selectedTag);
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
		OhLohTagEntity tag = (OhLohTagEntity)
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
