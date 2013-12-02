package br.ufba.dcc.mestrado.computacao.web.listener;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.ApiBinding;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.repository.base.UserRepository;

@Component
public class SocialPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3122605866980717526L;
	
	
	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	
	@Autowired
	private UsersConnectionRepository userConnectionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${application.url}")
	private String applicationUrl;

	@Override
	public void afterPhase(PhaseEvent event) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void beforePhase(PhaseEvent event) {
		
        
        for (String providerId : getConnectionFactoryLocator().registeredProviderIds()) {
    		ConnectionFactory<? extends ApiBinding> connectionFactory = (ConnectionFactory<? extends ApiBinding>) 
    				getConnectionFactoryLocator().getConnectionFactory(providerId);
    		
    		if (connectionFactory != null) {
    			if (connectionFactory instanceof FacebookConnectionFactory) {
    				FacebookConnectionFactory facebookConnectionFactory = (FacebookConnectionFactory) connectionFactory;
    				processFacebookConnection(facebookConnectionFactory);
    			} else if (connectionFactory instanceof TwitterConnectionFactory) {
    				TwitterConnectionFactory twitterConnectionFactory = (TwitterConnectionFactory) connectionFactory;
    				processTwitterConnection(twitterConnectionFactory);
    			}
    		}
    	}
	}
	
	public void processFacebookConnection(FacebookConnectionFactory connectionFactory) {
		
		Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String authorizationCode = paramMap.get("code");
		
		if (connectionFactory != null && authorizationCode != null) {
			AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(authorizationCode, getApplicationUrl(), null);
			Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
            Facebook facebook = connection.getApi();
            
            if (facebook.isAuthorized()) {
            	
            }
		}
	}
	
	public void processTwitterConnection(TwitterConnectionFactory connectionFactory) {
		if (connectionFactory != null) {
			//OAuthToken oAuthToken = connectionFactory.getOAuthOperations().fetchRequestToken(getApplicationUrl(), null);
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
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
	
	public UserRepository getUserRepository() {
		return userRepository;
	}
	
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public String getApplicationUrl() {
		return applicationUrl;
	}
	
	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}

}
