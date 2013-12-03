package br.ufba.dcc.mestrado.computacao.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import br.ufba.dcc.mestrado.computacao.service.impl.SpringSecuritySignInAdapterImpl;
import br.ufba.dcc.mestrado.computacao.social.connect.UserConnectionSignUp;

@Configuration
@PropertySource(
	name = "socialProperties",
	value = {	
			"classpath:social.properties"
	}
)
public class SocialAppConfig {
	@Autowired
	private Environment environment;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserConnectionSignUp connectionSignUp;

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
		JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(
				dataSource,
				connectionFactoryLocator(), 
				textEncryptor());
		
		
		usersConnectionRepository.setTablePrefix("social_");
		usersConnectionRepository.setConnectionSignUp(connectionSignUp);
		
		return usersConnectionRepository;
	}
	 
    
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
    	HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
    	return filter;
    }
	
    @Bean
    public ProviderSignInController providerSignInController() {
    	ProviderSignInController controller = new ProviderSignInController(
        		connectionFactoryLocator(), 
        		usersConnectionRepository(), 
        		new SpringSecuritySignInAdapterImpl());
    	
    	controller.setApplicationUrl(environment.getProperty("application.url"));
    	
    	return controller;
    }
}
