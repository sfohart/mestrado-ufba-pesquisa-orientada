package br.ufba.dcc.mestrado.computacao.web.managedbean.login;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.social.permissions.EmailFacebookPermissions;
import br.ufba.dcc.mestrado.computacao.social.permissions.ExtendedPublishFacebookPermissions;
import br.ufba.dcc.mestrado.computacao.social.permissions.ExtendedReadFacebookPermissions;

@ManagedBean(name = "facebookMB")
@SessionScoped
public class FacebookManagedBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6964331391812274964L;
	
	private static final Logger logger = Logger.getLogger(FacebookManagedBean.class.getName());

	private String applicationURL;
	
	@ManagedProperty("#{connectionFactoryLocator}")
	private ConnectionFactoryLocator connectionFactoryLocator; 
	
	@ManagedProperty("#{usersConnectionRepository}")
	private UsersConnectionRepository userConnectionRepository;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService repositoryBasedUserDetailsService;
	
	public FacebookManagedBean() {
		ResourceBundle bundle = ResourceBundle.getBundle("social");
		this.applicationURL = bundle.getString("application.url");
	}
	
	public String getApplicationURL() {
		return applicationURL;
	}
	
	public void setApplicationURL(String applicationURL) {
		this.applicationURL = applicationURL;
	}
	
	public ConnectionFactoryLocator getConnectionFactoryLocator() {
		return connectionFactoryLocator;
	}
	public void setConnectionFactoryLocator(
			ConnectionFactoryLocator connectionFactoryLocator) {
		this.connectionFactoryLocator = connectionFactoryLocator;
	}
	
	public UsersConnectionRepository getUserConnectionRepository() {
		return userConnectionRepository;
	}
	
	public void setUserConnectionRepository(UsersConnectionRepository userConnectionRepository) {
		this.userConnectionRepository = userConnectionRepository;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public RepositoryBasedUserDetailsService getRepositoryBasedUserDetailsService() {
		return repositoryBasedUserDetailsService;
	}
	
	public void setRepositoryBasedUserDetailsService(
			RepositoryBasedUserDetailsService repositoryBasedUserDetailsService) {
		this.repositoryBasedUserDetailsService = repositoryBasedUserDetailsService;
	}
	
	public String signInWithFacebook() throws IOException {
		if (getConnectionFactoryLocator() != null) {
			FacebookConnectionFactory connectionFactory = (FacebookConnectionFactory) getConnectionFactoryLocator().getConnectionFactory(Facebook.class);
			
			OAuth2Operations oAuth2Operations = connectionFactory.getOAuthOperations();
			OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
			
			configureFacebookScope(oAuth2Parameters);
			
			oAuth2Parameters.setRedirectUri(getApplicationURL());
			
			String authorizeURL = oAuth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(authorizeURL);
		}
		
		return null;
	}
	
	protected void configureFacebookScope(OAuth2Parameters oAuth2Parameters) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(ExtendedPublishFacebookPermissions.publish_stream);
		buffer.append(",");
		buffer.append(ExtendedReadFacebookPermissions.read_friendlists);
		buffer.append(",");
		buffer.append(ExtendedReadFacebookPermissions.user_online_presence);
		buffer.append(",");
		buffer.append(EmailFacebookPermissions.email);
		
		oAuth2Parameters.setScope(buffer.toString());
	}

	public void processFacebookLogin(ComponentSystemEvent event) {
		Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String authorizationCode = paramMap.get("code");
        
        if (! StringUtils.isEmpty(authorizationCode)) {
        	
        	logger.info("Código de autorização do Facebook recebido");
        	
        	FacebookConnectionFactory connectionFactory = (FacebookConnectionFactory) getConnectionFactoryLocator().getConnectionFactory(Facebook.class);
        	
        	
        	logger.info("Pedindo concessão de acesso ao Facebook");
        	
        	AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(authorizationCode, getApplicationURL(), null);
        	
        	logger.info("Criando conexão com o Facebook");
        	
        	Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);        	
        	Facebook facebook = connection.getApi();
        	
        	if (facebook.isAuthorized()) {
        		
        		logger.info("Facebook autorizou o login");
        		
        		FacebookProfile facebookProfile = facebook.userOperations().getUserProfile();
        		
        		String userId = null;
        		
        		List<String> userIds = getUserConnectionRepository().findUserIdsWithConnection(connection);
        		
        		if (userIds.isEmpty()) {
        			userId = facebookProfile.getUsername();
        			getUserConnectionRepository().createConnectionRepository(userId).addConnection(connection);
        			logger.info("Armazenando conexão com o Facebook");
        		} else if (userIds.size() == 1) {
        			userId = userIds.get(0);
        			getUserConnectionRepository().createConnectionRepository(userId).updateConnection(connection);
        			logger.info("Atualizando conexão com o Facebook");
        		} else {
        			
        		}
        		
        		if (! StringUtils.isEmpty(userId)) {
        			
        			logger.info("Logando na aplicação com usuário do Facebook");
        			
        			UserEntity userEntity = getUserService().findBySocialLogin(userId);
        			
        			List<GrantedAuthority> authorityList = (List<GrantedAuthority>) getRepositoryBasedUserDetailsService().getAuthorities(userEntity);
        			        			
        			UsernamePasswordAuthenticationToken authenticationToken = 
        					new UsernamePasswordAuthenticationToken(userId, null, authorityList);
        			
        			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        		}
        	}
        }
	}

}
