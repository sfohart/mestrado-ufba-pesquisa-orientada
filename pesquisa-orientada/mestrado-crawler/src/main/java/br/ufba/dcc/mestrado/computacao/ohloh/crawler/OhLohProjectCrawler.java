package br.ufba.dcc.mestrado.computacao.ohloh.crawler;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OhLohCrawlerProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohProjectResponse;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.spring.CrawlerAppConfig;

@Component
public class OhLohProjectCrawler {
	
	private static final String OHLOH_PROJECT_SORT_BY_ID = "id";

	public static Logger logger = Logger.getLogger(OhLohProjectCrawler.class);
	
	@Autowired
	private OhLohRestfulClient ohLohRestfulClient;
	
	@Autowired
	private OhLohProjectService ohLohProjectService;
	
	@Autowired
	private OhLohCrawlerProjectService projectCrawlerConfigService;
	
	@Autowired
	private OhLohLanguageCrawler ohLohLanguageCrawler;
	
	@Autowired
	private OhLohActivityFactCrawler ohLohActivityFactCrawler;
	
	@Autowired
	private OhLohEnlistmentCrawler ohLohEnlistmentCrawler;
	
	
	public OhLohRestfulClient getRestfulClient() {
		return ohLohRestfulClient;
	}

	public void setRestfulClient(OhLohRestfulClient ohLohRestfulClient) {
		this.ohLohRestfulClient = ohLohRestfulClient;
	}

	public OhLohProjectService getProjectService() {
		return ohLohProjectService;
	}

	public void setProjectService(OhLohProjectService ohLohProjectService) {
		this.ohLohProjectService = ohLohProjectService;
	}

	public OhLohCrawlerProjectService getProjectCrawlerConfigService() {
		return projectCrawlerConfigService;
	}

	public void setProjectCrawlerConfigService(
			OhLohCrawlerProjectService projectCrawlerConfigService) {
		this.projectCrawlerConfigService = projectCrawlerConfigService;
	}

	public OhLohLanguageCrawler getLanguageCrawler() {
		return ohLohLanguageCrawler;
	}

	public void setLanguageCrawler(OhLohLanguageCrawler ohLohLanguageCrawler) {
		this.ohLohLanguageCrawler = ohLohLanguageCrawler;
	}

	public OhLohActivityFactCrawler getActivityFactCrawler() {
		return ohLohActivityFactCrawler;
	}

	public void setActivityFactCrawler(
			OhLohActivityFactCrawler ohLohActivityFactCrawler) {
		this.ohLohActivityFactCrawler = ohLohActivityFactCrawler;
	}

	public OhLohEnlistmentCrawler getEnlistmentCrawler() {
		return ohLohEnlistmentCrawler;
	}

	public void setEnlistmentCrawler(
			OhLohEnlistmentCrawler ohLohEnlistmentCrawler) {
		this.ohLohEnlistmentCrawler = ohLohEnlistmentCrawler;
	}

	/**
	 * Não usar JOB aqui, openshift pode dar OutOfMemoryException 
	 */
	public void run() throws Exception {
		getLanguageCrawler().downloadLanguages();
		
		OhLohBaseRequest request = new OhLohBaseRequest();
		Integer totalPages = 0;
		Integer page = 1;
		
		request.setSort(OHLOH_PROJECT_SORT_BY_ID);
		
		OhLohCrawlerProjectEntity config = getProjectCrawlerConfigService().findCrawlerConfig();
		
		if (config != null) {
			if (config.getCurrentPage() != null) {
				page = config.getCurrentPage();
			} else {
				config.setCurrentPage(page);
			}
			
			if (config.getTotalPage() != null) {
				totalPages = config.getTotalPage();
			}
		} else {
			config = new OhLohCrawlerProjectEntity();
			config.setCurrentPage(page);
		}

		
		try {
			do {
				request.setPage(page);
				
				//baixando projetos
				OhLohProjectResponse response = ohLohRestfulClient.getAllProjects(request);
				logger.info(String.format("Total Pages: %d | Total Projects: %d", totalPages, getProjectService().countAll()));
				
				if (totalPages <= 0 && response.getItemsAvailable() != null && response.getItemsReturned() != null) {
					totalPages = response.getItemsAvailable() / response.getItemsReturned();
					if (response.getItemsAvailable() % response.getItemsReturned() > 0) {
						totalPages++;
					}
						
					
					config.setTotalPage(totalPages);
				}
				
				//Servidor retornou com sucesso. Armazene os dados dos projetos
				if (OhLohProjectResponse.SUCCESS.equals(response.getStatus())) {
					List<OhLohProjectDTO> ohLohProjectDTOs = response.getResult().getProjects();
					if (ohLohProjectDTOs != null && ! ohLohProjectDTOs.isEmpty()) {
						logger.info(String.format("Page: %d | Projects: %d", page, ohLohProjectDTOs.size()));
						
						//iterar cada projeto da lista retornada
						for (OhLohProjectDTO project : ohLohProjectDTOs) {
							if (getProjectService().findById(project.getId()) == null) {
								//armazenando projeto
								OhLohProjectEntity ohLohProject = getProjectService().process(project);								
								
								config.setProject(ohLohProject);
								config.setCurrentPage(page);
								config.setItemsAvailable(response.getItemsAvailable());
								config.setItemsPerPage(response.getItemsReturned());
								getProjectCrawlerConfigService().save(config);
								
								getEnlistmentCrawler().downloadEnlistments(ohLohProject);
								
								//downloadActivityFacts(ohLohProject);
								
								//downloadSizeFacts(ohLohProject);
								
							} else {
								logger.info(String.format("Projeto \"%s\" com id %d j� se encontra na base", project.getName(), project.getId()));
							}
						}
					}
					
				} else {
					throw new IllegalStateException("OhLoh response status: " + response.getStatus() + ". " + response.getError());
				}
				
				
				if (response.getItemsReturned() > 0) {
					page++;
				}

			
				config.setCurrentPage(page);
				config.setProject(null);
				config.setItemsAvailable(response.getItemsAvailable());
				config.setItemsPerPage(response.getItemsReturned());
				getProjectCrawlerConfigService().save(config);
				
			} while (page < totalPages);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			getProjectCrawlerConfigService().save(config);
		}
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = 
		          new AnnotationConfigApplicationContext(CrawlerAppConfig.class);
		
		OhLohProjectCrawler main = context.getBean(OhLohProjectCrawler.class);
		
		if (main != null) {
			main.run();
		}
		
		((AbstractApplicationContext) context).registerShutdownHook();
		((AbstractApplicationContext) context).close();
		
	}

}
