package br.ufba.dcc.mestrado.computacao.ohloh.crawler;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohProjectResponse;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerProjectRepository;
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
	private OhLohCrawlerProjectRepository crawlerConfigRepository;
	
	public OhLohRestfulClient getOhLohRestfulClient() {
		return ohLohRestfulClient;
	}

	public void setOhLohRestfulClient(OhLohRestfulClient ohLohRestfulClient) {
		this.ohLohRestfulClient = ohLohRestfulClient;
	}

	public OhLohProjectService getOhLohProjectService() {
		return ohLohProjectService;
	}
	
	public void setOhLohProjectService(OhLohProjectService ohLohProjectService) {
		this.ohLohProjectService = ohLohProjectService;
	}

	public OhLohProjectCrawler() {
	}
	
	public void run() throws Exception {
		OhLohBaseRequest request = new OhLohBaseRequest();
		Integer totalPages = 0;
		Integer page = 1;
		
		request.setSort(OHLOH_PROJECT_SORT_BY_ID);
		
		OhLohCrawlerProjectEntity config = new OhLohCrawlerProjectEntity();
		
		List<OhLohCrawlerProjectEntity> configList = 
				crawlerConfigRepository.findAll();
		
		if (configList != null && ! configList.isEmpty()) {
			config = configList.get(0);
			page = config.getCurrentPage();
		}
		
		
		try {
			do {
				request.setPage(page);
				OhLohProjectResponse response = ohLohRestfulClient.getAllProjects(request);
				logger.info(String.format("Total Pages: %d | Total Projects: %d", totalPages, ohLohProjectService.countAll()));
				
				if (totalPages <= 0 && response.getItemsAvailable() != null && response.getItemsReturned() != null) {
					totalPages = response.getItemsAvailable() / response.getItemsReturned();
					config.setTotalPage(totalPages);
				}
				
				if (OhLohProjectResponse.SUCCESS.equals(response.getStatus())) {
					List<OhLohProjectDTO> ohLohProjectDTOs = response.getResult().getOhLohProjects();
					if (ohLohProjectDTOs != null && ! ohLohProjectDTOs.isEmpty()) {
						logger.info(String.format("Page: %d | Projects: %d", page, ohLohProjectDTOs.size()));
						for (OhLohProjectDTO project : ohLohProjectDTOs) {
							if (ohLohProjectService.findById(project.getId()) == null) {
								ohLohProjectService.process(project);
							} else {
								logger.info(String.format("Projeto \"%s\" com id %d já se encontra na base", project.getName(), project.getId()));
							}
							
						}
					}
					
				} else {
					throw new IllegalStateException("OhLoh response status: " + response.getStatus() + ". " + response.getError());
				}
				
				
				page++;
			
				config.setCurrentPage(page);
				crawlerConfigRepository.save(config);
				
			} while (page < totalPages);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			crawlerConfigRepository.save(config);
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
		
	}

}
