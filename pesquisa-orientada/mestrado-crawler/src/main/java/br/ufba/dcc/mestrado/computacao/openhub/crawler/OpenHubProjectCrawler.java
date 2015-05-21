package br.ufba.dcc.mestrado.computacao.openhub.crawler;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerProjectEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectDTO;
import br.ufba.dcc.mestrado.computacao.openhub.restful.client.OpenHubRestfulClientImpl;
import br.ufba.dcc.mestrado.computacao.openhub.restful.request.OpenHubBaseRequest;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubProjectResponse;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubCrawlerProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubProjectService;
import br.ufba.dcc.mestrado.computacao.spring.CrawlerAppConfig;

@Component
public class OpenHubProjectCrawler {
	
	private static final String OHLOH_PROJECT_SORT_BY_ID = "id";

	public static Logger logger = Logger.getLogger(OpenHubProjectCrawler.class.getName());
	
	@Autowired
	private OpenHubRestfulClientImpl ohLohRestfulClient;
	
	@Autowired
	private OpenHubProjectService openHubProjectService;
	
	@Autowired
	private OpenHubCrawlerProjectService projectCrawlerConfigService;
	
	@Autowired
	private OpenHubLanguageCrawler openHubLanguageCrawler;
	
	@Autowired
	private OpenHubActivityFactCrawler openHubActivityFactCrawler;
	
	@Autowired
	private OpenHubEnlistmentCrawler openHubEnlistmentCrawler;
	
	
	public OpenHubRestfulClientImpl getRestfulClient() {
		return ohLohRestfulClient;
	}

	public void setRestfulClient(OpenHubRestfulClientImpl ohLohRestfulClient) {
		this.ohLohRestfulClient = ohLohRestfulClient;
	}

	public OpenHubProjectService getProjectService() {
		return openHubProjectService;
	}

	public void setProjectService(OpenHubProjectService openHubProjectService) {
		this.openHubProjectService = openHubProjectService;
	}

	public OpenHubCrawlerProjectService getProjectCrawlerConfigService() {
		return projectCrawlerConfigService;
	}

	public void setProjectCrawlerConfigService(
			OpenHubCrawlerProjectService projectCrawlerConfigService) {
		this.projectCrawlerConfigService = projectCrawlerConfigService;
	}

	public OpenHubLanguageCrawler getLanguageCrawler() {
		return openHubLanguageCrawler;
	}

	public void setLanguageCrawler(OpenHubLanguageCrawler openHubLanguageCrawler) {
		this.openHubLanguageCrawler = openHubLanguageCrawler;
	}

	public OpenHubActivityFactCrawler getActivityFactCrawler() {
		return openHubActivityFactCrawler;
	}

	public void setActivityFactCrawler(
			OpenHubActivityFactCrawler openHubActivityFactCrawler) {
		this.openHubActivityFactCrawler = openHubActivityFactCrawler;
	}

	public OpenHubEnlistmentCrawler getEnlistmentCrawler() {
		return openHubEnlistmentCrawler;
	}

	public void setEnlistmentCrawler(
			OpenHubEnlistmentCrawler openHubEnlistmentCrawler) {
		this.openHubEnlistmentCrawler = openHubEnlistmentCrawler;
	}

	/**
	 * NÃ£o usar JOB aqui, openshift pode dar OutOfMemoryException 
	 */
	public void run() throws Exception {
		getLanguageCrawler().downloadLanguages();
		
		OpenHubBaseRequest request = new OpenHubBaseRequest();
		Integer totalPages = 0;
		Integer page = 1;
		
		request.setSort(OHLOH_PROJECT_SORT_BY_ID);
		
		OpenHubCrawlerProjectEntity config = getProjectCrawlerConfigService().findCrawlerConfig();
		
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
			config = new OpenHubCrawlerProjectEntity();
			config.setCurrentPage(page);
		}

		
		try {
			do {
				request.setPage(page);
				
				//baixando projetos
				OpenHubProjectResponse response = ohLohRestfulClient.getAllProjects(request);
				logger.info(String.format("Total Pages: %d | Total Projects: %d", totalPages, getProjectService().countAll()));
				
				if (totalPages <= 0 && response.getItemsAvailable() != null && response.getItemsReturned() != null) {
					totalPages = response.getItemsAvailable() / response.getItemsReturned();
					if (response.getItemsAvailable() % response.getItemsReturned() > 0) {
						totalPages++;
					}
						
					
					config.setTotalPage(totalPages);
				}
				
				//Servidor retornou com sucesso. Armazene os dados dos projetos
				if (OpenHubProjectResponse.SUCCESS.equals(response.getStatus())) {
					List<OpenHubProjectDTO> openHubProjectDTOs = response.getResult().getProjects();
					if (openHubProjectDTOs != null && ! openHubProjectDTOs.isEmpty()) {
						logger.info(String.format("Page: %d | Projects: %d", page, openHubProjectDTOs.size()));
						
						//iterar cada projeto da lista retornada
						for (OpenHubProjectDTO project : openHubProjectDTOs) {
							if (getProjectService().findById(Long.valueOf(project.getId())) == null) {
								//armazenando projeto
								OpenHubProjectEntity ohLohProject = getProjectService().process(project);								
								
								config.setProject(ohLohProject);
								config.setCurrentPage(page);
								config.setItemsAvailable(response.getItemsAvailable());
								config.setItemsPerPage(response.getItemsReturned());
								getProjectCrawlerConfigService().save(config);
								
								getEnlistmentCrawler().downloadEnlistments(ohLohProject);
								
								//downloadActivityFacts(ohLohProject);
								
								//downloadSizeFacts(ohLohProject);
								
							} else {
								logger.info(String.format("Projeto \"%s\" com id %s já se encontra na base", project.getName(), project.getId()));
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
		
		OpenHubProjectCrawler main = context.getBean(OpenHubProjectCrawler.class);
		
		if (main != null) {
			main.run();
		}
		
		((AbstractApplicationContext) context).registerShutdownHook();
		((AbstractApplicationContext) context).close();
		
	}

}
