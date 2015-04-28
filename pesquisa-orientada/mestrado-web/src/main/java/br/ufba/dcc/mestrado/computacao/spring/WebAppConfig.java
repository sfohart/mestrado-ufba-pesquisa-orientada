package br.ufba.dcc.mestrado.computacao.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("br.ufba.dcc.mestrado.computacao")
@Import({
	RecommenderAppConfig.class,		//Configura��es do m�dulo de recomenda��o
	SocialAppConfig.class,
	SecurityAppConfig.class,		//Configura��es do m�dulo de seguran�a
	BatchAppConfig.class			//Configura��es de agendamento de tarefas
})
public class WebAppConfig {
	
	
}
