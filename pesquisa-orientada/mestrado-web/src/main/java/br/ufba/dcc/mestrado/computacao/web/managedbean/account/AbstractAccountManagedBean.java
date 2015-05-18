package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.TagService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.PreferenceReviewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;

public class AbstractAccountManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6887186105967430118L;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	protected RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{projectDetailPageViewService}")
	protected ProjectDetailPageViewService pageViewService;
	
	@ManagedProperty("#{overallRatingService}")
	protected OverallRatingService overallRatingService;
	
	@ManagedProperty("#{preferenceReviewService}")
	protected PreferenceReviewService preferenceReviewService;
	
	@ManagedProperty("#{tagService}")
	protected TagService tagService;
	
	@ManagedProperty("#{userService}")
	protected UserService userService;
	
	@ManagedProperty("#{standardPasswordEncoder}")
	protected PasswordEncoder passwordEncoder;
	
	private UserEntity account;
	
	private boolean loggedUserOnly;
	private boolean loggedUser;
	
	public AbstractAccountManagedBean() {
		this.account = new UserEntity();
	}
	
	public void init(ComponentSystemEvent event) {
		if (getAccount() != null && getAccount().getId() != null) {
			this.account = getUserService().findById(getAccount().getId());
			
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
			
			this.loggedUser = false;
		} else {
			this.loggedUser = true;
		}
	}
	
	public String getEncodedEmail() {
		String encoded = "";
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			
			if (getAccount() != null && ! StringUtils.isEmpty(getAccount().getEmail())) {
				byte[] bytes = getAccount().getEmail().getBytes("CP1252");
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

	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public ProjectDetailPageViewService getPageViewService() {
		return pageViewService;
	}

	public void setPageViewService(ProjectDetailPageViewService pageViewService) {
		this.pageViewService = pageViewService;
	}

	public OverallRatingService getOverallRatingService() {
		return overallRatingService;
	}

	public void setOverallRatingService(OverallRatingService overallRatingService) {
		this.overallRatingService = overallRatingService;
	}

	public PreferenceReviewService getPreferenceReviewService() {
		return preferenceReviewService;
	}

	public void setPreferenceReviewService(
			PreferenceReviewService preferenceReviewService) {
		this.preferenceReviewService = preferenceReviewService;
	}

	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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
	
	public void setLoggedUser(boolean loggedUser) {
		this.loggedUser = loggedUser;
	}
	
	public boolean isLoggedUser() {
		return this.loggedUser;
	}

}
