package br.ufba.dcc.mestrado.computacao.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
@PropertySource(value = {	
	"classpath:social.properties"
})
public class SocialAppConfig {
	@Autowired
	private Environment environment;
	
	@Autowired
	private DataSource dataSource;

	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
	    ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
	    registry.addConnectionFactory(
	    		new FacebookConnectionFactory(
	    				environment.getProperty("facebook.clientId"),
	    				environment.getProperty("facebook.clientSecret")
	    			)
	    	);
	    
	    return registry;
	}
	
	@Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }
	
	@Bean
	public UsersConnectionRepository usersConnectionRepository() {
		return new JdbcUsersConnectionRepository(
				dataSource,
				connectionFactoryLocator(), 
				textEncryptor());
	}
	
	@Bean
    public ConnectionRepository connectionRepository() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        /*if (authentication == null)
            throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
        
        return usersConnectionRepository().createConnectionRepository(authentication.getName());*/
        
        if (authentication != null) {
        	return usersConnectionRepository().createConnectionRepository(authentication.getName());
        } else {
        	return null;
        }
    }
	
    @Bean
    public ConnectController connectController() {
    	ConnectController controller = new ConnectController(connectionFactoryLocator(), connectionRepository());
        controller.setApplicationUrl(environment.getRequiredProperty("application.url"));
        return controller;
    }
    
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
    	HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
    	return filter;
    }
	
	
}
