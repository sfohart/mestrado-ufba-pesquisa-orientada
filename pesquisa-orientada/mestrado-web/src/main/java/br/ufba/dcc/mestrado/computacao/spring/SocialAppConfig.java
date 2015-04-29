package br.ufba.dcc.mestrado.computacao.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.ufba.dcc.mestrado.computacao.service.basic.SpringSecuritySignInAdapter;
import br.ufba.dcc.mestrado.computacao.social.connect.AccountConnectionSignUp;


@Configuration
@EnableSocial
@PropertySource(value = {	
		"classpath:social.properties"
	})
public class SocialAppConfig extends WebMvcConfigurerAdapter implements SocialConfigurer {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AccountConnectionSignUp connectionSignUp;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private SpringSecuritySignInAdapter springSecuritySignInAdapter;
	
	@Override
	public void addConnectionFactories(
			ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {
		
		connectionFactoryConfigurer.addConnectionFactory(
	    		new FacebookConnectionFactory(
	    				environment.getProperty("facebook.appId"),
	    				environment.getProperty("facebook.appSecret")
	    			)
	    	);
		
		connectionFactoryConfigurer.addConnectionFactory(
	    		new TwitterConnectionFactory(
	    				environment.getProperty("twitter.consumerKey"),
	    				environment.getProperty("twitter.consumerSecret")
	    			)
	    	);
		
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(
				dataSource,
				connectionFactoryLocator, 
				textEncryptor());
		
		
		usersConnectionRepository.setTablePrefix("social_");
		usersConnectionRepository.setConnectionSignUp(connectionSignUp);
		
		return usersConnectionRepository;
	}
	
	@Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }
	
	/*@Bean
    public ConnectController connectControllerBean(
                ConnectionFactoryLocator connectionFactoryLocator,
                ConnectionRepository connectionRepository) throws Exception {
		
		
		ConnectController connectController = new ConnectController(connectionFactoryLocator, connectionRepository);
		connectController.afterPropertiesSet();
		connectController.setApplicationUrl(environment.getProperty("application.url"));
		
		return connectController;
    }*/
	
	
	
	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
	
	@Bean
	public ProviderSignInController providerSignInController(
	            ConnectionFactoryLocator connectionFactoryLocator,
	            UsersConnectionRepository usersConnectionRepository) throws Exception {
		
		ProviderSignInController controller = new ProviderSignInController(
	        connectionFactoryLocator,
	        usersConnectionRepository,
	        springSecuritySignInAdapter);
		
		controller.afterPropertiesSet();
		
		controller.setApplicationUrl(environment.getProperty("application.url"));
		
		return controller;
	}

}
