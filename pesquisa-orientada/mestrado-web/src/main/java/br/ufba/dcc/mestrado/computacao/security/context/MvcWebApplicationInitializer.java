package br.ufba.dcc.mestrado.computacao.security.context;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.ufba.dcc.mestrado.computacao.spring.SocialAppConfig;

@Component
public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer  {

	   @Override
	    protected Class<?>[] getRootConfigClasses() {
	        return null;
	    }

	    @Override
	    protected Class<?>[] getServletConfigClasses() {
	        return new Class[] { SocialAppConfig.class };
	    }

	    @Override
	    protected String[] getServletMappings() {
	        return new String[] { "/" };
	    }

	

}
