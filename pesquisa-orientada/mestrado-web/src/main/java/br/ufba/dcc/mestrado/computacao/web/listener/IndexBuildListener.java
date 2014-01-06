package br.ufba.dcc.mestrado.computacao.web.listener;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.ufba.dcc.mestrado.computacao.service.base.Indexer;

/**
 * Application Lifecycle Listener implementation class IndexListener
 *
 */
@Component
public class IndexBuildListener implements ServletContextListener {
	
	private static final Logger logger = Logger.getLogger(IndexBuildListener.class.getName());
	
	@Autowired
	private Indexer indexer;

    /**
     * Default constructor. 
     */
    public IndexBuildListener() {
    }

    public Indexer getIndexer() {
		return indexer;
	}
    
    public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}
    
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	//aplicação (re)inicializada. (Re)criar o índice
    	try {
    		WebApplicationContextUtils
	            .getRequiredWebApplicationContext(event.getServletContext())
	            .getAutowireCapableBeanFactory()
	            .autowireBean(this);
    		
    		
    		logger.info("(Re)criando o índice do Apache Lucene/Hibernate Search");
			indexer.buildIndex();
			logger.info("Índice (re)criado");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
        // TODO Auto-generated method stub
    }
	
}
