package br.ufba.dcc.mestrado.computacao.security.context;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.MultipartFilter;

import br.ufba.dcc.mestrado.computacao.spring.SecurityAppConfig;

@Component
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	public SecurityWebApplicationInitializer() {
		super(SecurityAppConfig.class);
	}
	
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		insertFilters(servletContext, new MultipartFilter());
		
	}
	
}
