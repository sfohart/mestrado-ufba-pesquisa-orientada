package br.ufba.dcc.mestrado.computacao.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("br.ufba.dcc.mestrado.computacao")
@Import({
	WebMvcAppConfig.class,
	RecommenderAppConfig.class,		
	SocialAppConfig.class,
	SecurityAppConfig.class,
	BatchAppConfig.class			
})
public class WebAppConfig {
	
	
}
