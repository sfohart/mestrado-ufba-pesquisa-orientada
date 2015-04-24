package br.ufba.dcc.mestrado.computacao.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("br.ufba.dcc.mestrado.computacao")
@Import({
	RecommenderAppConfig.class,		//Configurações do módulo de recomendação
	SocialAppConfig.class,			//Configurações da integração com redes sociais (a implementar)
	SecurityAppConfig.class,		//Configurações do módulo de segurança
	ScheduleAppConfig.class			//Configurações de agendamento de tarefas
})
public class WebAppConfig {
	
	
	
	
}
