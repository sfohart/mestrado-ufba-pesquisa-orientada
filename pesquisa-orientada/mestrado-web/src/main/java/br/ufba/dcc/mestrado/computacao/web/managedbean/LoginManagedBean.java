package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.social.ApiBinding;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

@ManagedBean(name="loginMB")
@ViewScoped
public class LoginManagedBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 238087893391391911L;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{connectionFactoryLocator}")
	private ConnectionFactoryLocator connectionFactoryLocator; 
	
	@ManagedProperty("#{usersConnectionRepository}")
	private UsersConnectionRepository userConnectionRepository;
	
	private String applicationURL;
	
	private UserEntity loggedUser;
	
	public LoginManagedBean() {
		ResourceBundle bundle = ResourceBundle.getBundle("social");
		
		this.applicationURL = bundle.getString("application.url");
	}
	
	public UserEntity getLoggedUser() {
		if (this.loggedUser == null) {
			this.loggedUser = getUserDetailsService().loadFullLoggedUser();
		}
		
		return this.loggedUser;
	}
	
	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	
	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	public ConnectionFactoryLocator getConnectionFactoryLocator() {
		return connectionFactoryLocator;
	}
	
	public void setConnectionFactoryLocator(ConnectionFactoryLocator connectionFactoryLocator) {
		this.connectionFactoryLocator = connectionFactoryLocator;
	}
	
	public UsersConnectionRepository getUserConnectionRepository() {
		return userConnectionRepository;
	}
	
	public void setUserConnectionRepository(UsersConnectionRepository userConnectionRepository) {
		this.userConnectionRepository = userConnectionRepository;
	}
	
	public String getApplicationURL() {
		return applicationURL;
	}
	
	public void setApplicationURL(String applicationURL) {
		this.applicationURL = applicationURL;
	}
	
	public String signIn() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");
		
		dispatcher.forward(
				((ServletRequest) context.getRequest()), 
				((ServletResponse) context.getResponse()));
		
		FacesContext.getCurrentInstance().responseComplete();
		
		return null;
	}
	
	public String signInWithFacebook() throws IOException {
		if (getConnectionFactoryLocator() != null) {
			FacebookConnectionFactory connectionFactory = (FacebookConnectionFactory) getConnectionFactoryLocator().getConnectionFactory(Facebook.class);
			
			OAuth2Operations oAuth2Operations = connectionFactory.getOAuthOperations();
			OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
			
			oAuth2Parameters.setRedirectUri(getApplicationURL());
			
			String authorizeURL = oAuth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(authorizeURL);
		}
		
		return null;
	}
	
	public String signOut() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_logout");
		
		dispatcher.forward(
				((ServletRequest) context.getRequest()), 
				((ServletResponse) context.getResponse()));
		
		FacesContext.getCurrentInstance().responseComplete();
		
		return null;
	}
	

}
