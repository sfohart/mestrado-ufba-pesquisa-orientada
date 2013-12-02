package br.ufba.dcc.mestrado.computacao.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import br.ufba.dcc.mestrado.computacao.web.listener.SocialPhaseListener;

@Configuration
@PropertySource(value = {	
	"classpath:social.properties"
})
public class SocialAppConfig {
	@Autowired
	private Environment environment;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean(name = "socialProperties")
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		
		return placeholderConfigurer;
	}

	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
	    ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
	    
	    registry.addConnectionFactory(
	    		new FacebookConnectionFactory(
	    				environment.getProperty("facebook.clientId"),
	    				environment.getProperty("facebook.clientSecret")
	    			)
	    	);
	    
	    registry.addConnectionFactory(
	    		new TwitterConnectionFactory (
	    				environment.getProperty("twitter.consumerKey"),
	    				environment.getProperty("twitter.consumerSecret")
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
		
		JdbcUsersConnectionRepository connectionRepository = new JdbcUsersConnectionRepository(
				dataSource,
				connectionFactoryLocator(), 
				textEncryptor());
		
		connectionRepository.setTablePrefix("social_");
		
		return connectionRepository;
	}
	
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
    	HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
    	return filter;
    }
    
    @Bean
    public ConnectSupport connectSupport() {
    	ConnectSupport connectSupport = new ConnectSupport();
    	
    	connectSupport.setApplicationUrl(environment.getProperty("application.url"));
    	
    	return connectSupport;
    }
	
}
