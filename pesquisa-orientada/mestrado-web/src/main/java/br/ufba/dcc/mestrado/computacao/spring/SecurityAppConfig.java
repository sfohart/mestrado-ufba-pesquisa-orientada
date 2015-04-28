package br.ufba.dcc.mestrado.computacao.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.security.SpringSocialConfigurer;

import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

@Configuration
@EnableWebSecurity
//@ImportResource({"classpath:META-INF/spring-security.xml"})
public class SecurityAppConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@Bean
	public CsrfTokenRepository csrfTokenRepository() {
		return new HttpSessionCsrfTokenRepository();
	}

	@Bean(name = "standardPasswordEncoder")
	public PasswordEncoder standardPasswordEncoder() {
		return new StandardPasswordEncoder();
	}
	
	@Autowired
	public void configureAuthenticationManagerBuilder (AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService).passwordEncoder(standardPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers(
						"/resources",
						"/restful/**",
						"/javax.faces.resource/**");
	}
	
	@Configuration
	@Order(1)
	public static class CsrfWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private CsrfTokenRepository csrfTokenRepository;
		
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf()
					.csrfTokenRepository(csrfTokenRepository)
					.ignoringAntMatchers(
							"/detail/**",
							"/summary/**",
							"/reviews/project/**",
							"/reviews/user/**",
							"/search/results.jsf");
		}
					
	}
	
	
	@Configuration
	@Order(2)
	public static class DefaultWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.antMatchers(
							"/",
							"/account/new").permitAll()
					.antMatchers(
							"/account/*",
							"/reviews/**/new").hasRole("USER")
					.and()
				.formLogin()
					.loginPage("/account/login")
					.loginProcessingUrl("/signin/authenticate")
					.failureUrl("/account/login")
					.permitAll()
					.and()
				.logout()
					.invalidateHttpSession(true)
					//see http://docs.spring.io/spring-security/site/docs/4.0.1.RELEASE/reference/htmlsingle/#csrf-logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/account/logout"))
					.logoutSuccessUrl("/")
					.and()
				.exceptionHandling()
					.and()
				.httpBasic()
					.and()	
				.portMapper()
					.http(80).mapsTo(443)
					.http(8080).mapsTo(8443);
		}
	}
	
	
	@Configuration
	@Order(3)
	public static class SocialWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http
				.apply(new SpringSocialConfigurer());
		}		
	}
	
	
	@Configuration
	@Order(4)
	public static class SessionWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http
				.sessionManagement()
					.invalidSessionUrl("/account/login")
					.maximumSessions(1);
		}
	}
	
	
}
