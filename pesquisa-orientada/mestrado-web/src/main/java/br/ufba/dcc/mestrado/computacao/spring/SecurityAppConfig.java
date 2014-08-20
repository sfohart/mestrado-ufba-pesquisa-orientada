package br.ufba.dcc.mestrado.computacao.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
@ImportResource({"classpath:META-INF/spring-security.xml"})
public class SecurityAppConfig {

	@Bean
    public DelegatingFilterProxy springSecurityFilterChain() {
    	DelegatingFilterProxy filterProxy =  new DelegatingFilterProxy();
        return filterProxy;
    }	
	
	@Bean(name = "standardPasswordEncoder")
	public PasswordEncoder standardPasswordEncoder() {
		return new StandardPasswordEncoder();
	}
}