package br.ufba.dcc.mestrado.computacao.openhub.crawler;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.enlistment.OpenHubEnlistmentDTO;
import br.ufba.dcc.mestrado.computacao.openhub.restful.client.OpenHubRestfulClientImpl;
import br.ufba.dcc.mestrado.computacao.openhub.restful.request.OpenHubBaseRequest;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubEnlistmentResponse;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubCrawlerEnlistmentService;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubEnlistmentService;

@Component
public class OpenHubEnlistmentCrawler {
	
	private static final Logger logger = Logger.getLogger(OpenHubEnlistmentCrawler.class.getName());
	
	@Autowired
	private OpenHubCrawlerEnlistmentService openHubCrawlerEnlistmentService;
	
	@Autowired
	private OpenHubRestfulClientImpl ohLohRestfulClient;
	
	@Autowired
	private OpenHubEnlistmentService openHubEnlistmentService;

	public OpenHubCrawlerEnlistmentService getCrawlerEnlistmentService() {
		return openHubCrawlerEnlistmentService;
	}

	public void setOhLohCrawlerEnlistmentService(
			OpenHubCrawlerEnlistmentService openHubCrawlerEnlistmentService) {
		this.openHubCrawlerEnlistmentService = openHubCrawlerEnlistmentService;
	}

	public OpenHubRestfulClientImpl getRestfulClient() {
		return ohLohRestfulClient;
	}

	public void setOhLohRestfulClient(OpenHubRestfulClientImpl ohLohRestfulClient) {
		this.ohLohRestfulClient = ohLohRestfulClient;
	}

	public OpenHubEnlistmentService getEnlistmentService() {
		return openHubEnlistmentService;
	}

	public void setOhLohEnlistmentService(
			OpenHubEnlistmentService openHubEnlistmentService) {
		this.openHubEnlistmentService = openHubEnlistmentService;
	}
	
	public void downloadEnlistments(List<OpenHubProjectEntity> projectList) throws Exception {
		if (projectList != null) {
			for (OpenHubProjectEntity project : projectList) {
				downloadEnlistments(project);
			}
		}
	}
	
	public void downloadEnlistments(OpenHubProjectEntity project) throws Exception {
		OpenHubBaseRequest request = new OpenHubBaseRequest();
		
		Integer totalPages = 0;
		Integer page = 1;
		
		OpenHubCrawlerEnlistmentEntity config = getCrawlerEnlistmentService().findCrawlerConfig();
		if (config == null) {
			config = new OpenHubCrawlerEnlistmentEntity();
			config.setCurrentPage(page);
		} else {
			if (config.getCurrentPage() != null) {
				page = config.getCurrentPage();
			}
			
			if (config.getTotalPage() != null) {
				totalPages = config.getTotalPage(); 
			}
		}
		
		if (config.getProject() == null || ! config.getProject().equals(project)) {
			config.setProject(project);
			config.setTotalPage(null);
			config.setItemsAvailable(null);
			config.setItemsPerPage(null);
			config.setCurrentPage(1);
			
			page = 1;
			totalPages = 0;
		}
		
		if (project != null && project.getId() != null) {
			try {
				do {
					request.setPage(config.getCurrentPage());
					
					logger.info(String.format("Baixando enlistments para o projeto %d - página %d", project.getId(), request.getPage()));
					
					OpenHubEnlistmentResponse response = getRestfulClient().getAllProjectEnlistments(project.getId().toString(), request);
					if (totalPages <= 0 
							&& response != null 
							&& response.getItemsAvailable() != null && response.getItemsAvailable() != 0 
							&& response.getItemsReturned() != null && response.getItemsReturned() != 0) {
						totalPages = response.getItemsAvailable() / response.getItemsReturned();
						
						if (response.getItemsAvailable() % response.getItemsReturned() > 0) {
							totalPages++;
						}
						
						config.setTotalPage(totalPages);
					}
					
					if (OpenHubEnlistmentResponse.SUCCESS.equals(response.getStatus())) {
						List<OpenHubEnlistmentDTO> openHubEnlistmentDTOs = response.getResult().getEnlistments();
						if (openHubEnlistmentDTOs != null && ! openHubEnlistmentDTOs.isEmpty()) {
							for (OpenHubEnlistmentDTO enlistment : openHubEnlistmentDTOs) {
								getEnlistmentService().process(enlistment);
							}
						}
					}
					

					if (response.getItemsReturned() > 0) {
						page++;
					}
					
					config.setProject(project);
					config.setCurrentPage(page);
					config.setItemsAvailable(response.getItemsAvailable());
					config.setItemsPerPage(response.getItemsReturned());
					
					
				} while (page < totalPages);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				getCrawlerEnlistmentService().save(config);
			}
		}
	}
	

}
