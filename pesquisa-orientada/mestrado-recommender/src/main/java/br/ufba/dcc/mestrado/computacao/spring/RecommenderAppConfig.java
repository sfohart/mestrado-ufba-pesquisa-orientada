package br.ufba.dcc.mestrado.computacao.spring;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.ufba.dcc.mestrado.computacao.service.RecommenderServiceConfig;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("br.ufba.dcc.mestrado.computacao")
@Import(RecommenderServiceConfig.class)
@PropertySource(value = {	
	"classpath:model.properties"
})
public class RecommenderAppConfig {
	
	
	
}
