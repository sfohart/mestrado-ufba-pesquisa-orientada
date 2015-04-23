package br.ufba.dcc.mestrado.computacao.restful;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("restful")
public class RESTfulApp extends ResourceConfig {

	public RESTfulApp() {
		packages(true, "br.ufba.dcc.mestrado.computacao");
	}
	
}
