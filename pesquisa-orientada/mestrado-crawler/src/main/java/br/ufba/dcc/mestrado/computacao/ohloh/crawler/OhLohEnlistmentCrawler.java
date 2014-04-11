package br.ufba.dcc.mestrado.computacao.ohloh.crawler;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.enlistment.OhLohEnlistmentDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.entities.OhLohCrawlerEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohEnlistmentResponse;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerEnlistmentService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohEnlistmentService;

@Component
public class OhLohEnlistmentCrawler {
	
	private static final Logger logger = Logger.getLogger(OhLohEnlistmentCrawler.class.getName());
	
	@Autowired
	private OhLohCrawlerEnlistmentService ohLohCrawlerEnlistmentService;
	
	@Autowired
	private OhLohRestfulClient ohLohRestfulClient;
	
	@Autowired
	private OhLohEnlistmentService ohLohEnlistmentService;

	public OhLohCrawlerEnlistmentService getCrawlerEnlistmentService() {
		return ohLohCrawlerEnlistmentService;
	}

	public void setOhLohCrawlerEnlistmentService(
			OhLohCrawlerEnlistmentService ohLohCrawlerEnlistmentService) {
		this.ohLohCrawlerEnlistmentService = ohLohCrawlerEnlistmentService;
	}

	public OhLohRestfulClient getRestfulClient() {
		return ohLohRestfulClient;
	}

	public void setOhLohRestfulClient(OhLohRestfulClient ohLohRestfulClient) {
		this.ohLohRestfulClient = ohLohRestfulClient;
	}

	public OhLohEnlistmentService getEnlistmentService() {
		return ohLohEnlistmentService;
	}

	public void setOhLohEnlistmentService(
			OhLohEnlistmentService ohLohEnlistmentService) {
		this.ohLohEnlistmentService = ohLohEnlistmentService;
	}
	
	public void downloadEnlistments(List<OhLohProjectEntity> projectList) throws Exception {
		if (projectList != null) {
			for (OhLohProjectEntity project : projectList) {
				downloadEnlistments(project);
			}
		}
	}
	
	public void downloadEnlistments(OhLohProjectEntity project) throws Exception {
		OhLohBaseRequest request = new OhLohBaseRequest();
		
		Integer totalPages = 0;
		Integer page = 1;
		
		OhLohCrawlerEnlistmentEntity config = getCrawlerEnlistmentService().findCrawlerConfig();
		if (config == null) {
			config = new OhLohCrawlerEnlistmentEntity();
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
					
					OhLohEnlistmentResponse response = getRestfulClient().getAllProjectEnlistments(project.getId().toString(), request);
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
					
					if (OhLohEnlistmentResponse.SUCCESS.equals(response.getStatus())) {
						List<OhLohEnlistmentDTO> ohLohEnlistmentDTOs = response.getResult().getEnlistments();
						if (ohLohEnlistmentDTOs != null && ! ohLohEnlistmentDTOs.isEmpty()) {
							for (OhLohEnlistmentDTO enlistment : ohLohEnlistmentDTOs) {
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
