package br.ufba.dcc.mestrado.computacao.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

@Configuration
@EnableWebMvc
@EnableWebSecurity
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
						"/signin/**",
						"/javax.faces.resource/**");
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.sessionManagement()
				.invalidSessionUrl("/account/login")
				.maximumSessions(1);
		
		http
			.csrf()
				.csrfTokenRepository(csrfTokenRepository())
				.ignoringAntMatchers(
						"/detail/**",
						"/summary/**",
						"/reviews/project/**",
						"/reviews/user/**",
						"/search/results.jsf")
				.and()
			.authorizeRequests()
				.antMatchers(
						"/",
						"/favicon.ico",
						"/account/new").permitAll()
				.antMatchers(
						"/account/*",
						"/reviews/**/new").hasRole("USER")
				.and()
			.formLogin()
				.loginPage("/account/login")
				.loginProcessingUrl("/account/authenticate")
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
			.httpBasic();
			/*
			 * As linhas abaixo provocavam um NoSuchMethodError SocialAuthenticationFilter.getFilterProcessesUrl()
			 * o método é utilizado no spring-social-security 1.1.0.RELEASE, mas foi removido da classe AbstractAuthenticationProcessingFilter no 
			 * spring-security-web 4.0.1.
			 * Neste caso, não seguir o tutorial descrito em 
			 * http://docs.spring.io/spring-social/docs/1.1.0.RELEASE/reference/htmlsingle/#enabling-provider-sign-in-with-code-socialauthenticationfilter-code
			 */
			//	.and()
			//.apply(new SpringSocialConfigurer());
				
		
	}
	
	
	
	
}
