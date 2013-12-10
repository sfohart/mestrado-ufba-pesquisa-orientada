package br.ufba.dcc.mestrado.computacao.ohloh.crawler;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerLanguageEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.activityfact.OhLohActivityFactDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.activityfact.OhLohActivityFactResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.enlistment.OhLohEnlistmentDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.sizefact.OhLohSizeFactDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.sizefact.OhLohSizeFactResult;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohActivityFactResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohEnlistmentResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohLanguageResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohProjectResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohSizeFactResponse;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohActivityFactService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerEnlistmentService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerLanguageService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerLicenseService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohEnlistmentService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLanguageService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLicenseService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohSizeFactService;
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
	private OhLohLanguageService ohLanguageService;
	
	@Autowired
	private OhLohCrawlerLanguageService languageCrawlerConfigService;
	
	@Autowired
	private OhLohEnlistmentService ohLohEnlistmentService;
	
	@Autowired
	private OhLohCrawlerEnlistmentService enlistmentCrawlerConfigService;
	
	@Autowired
	private OhLohCrawlerProjectService projectCrawlerConfigService;
	
	@Autowired
	private OhLohCrawlerLicenseService licenseCrawlerConfigService;
	
	@Autowired
	private OhLohLicenseService ohLohLicenseService;
	
	@Autowired
	private OhLohActivityFactService ohLohActivityFactService;
	
	@Autowired
	private OhLohSizeFactService ohLohSizeFactService;
	
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
	
	public OhLohLanguageService getOhLanguageService() {
		return ohLanguageService;
	}

	public void setOhLanguageService(OhLohLanguageService ohLanguageService) {
		this.ohLanguageService = ohLanguageService;
	}

	public OhLohCrawlerLanguageService getLanguageCrawlerConfigService() {
		return languageCrawlerConfigService;
	}

	public void setLanguageCrawlerConfigService(
			OhLohCrawlerLanguageService languageCrawlerConfigService) {
		this.languageCrawlerConfigService = languageCrawlerConfigService;
	}

	public OhLohEnlistmentService getOhLohEnlistmentService() {
		return ohLohEnlistmentService;
	}

	public void setOhLohEnlistmentService(
			OhLohEnlistmentService ohLohEnlistmentService) {
		this.ohLohEnlistmentService = ohLohEnlistmentService;
	}
	
	public OhLohCrawlerEnlistmentService getEnlistmentCrawlerConfigService() {
		return enlistmentCrawlerConfigService;
	}

	public void setEnlistmentCrawlerConfigService(
			OhLohCrawlerEnlistmentService enlistmentCrawlerConfigService) {
		this.enlistmentCrawlerConfigService = enlistmentCrawlerConfigService;
	}

	public OhLohCrawlerProjectService getProjectCrawlerConfigService() {
		return projectCrawlerConfigService;
	}

	public void setProjectCrawlerConfigService(
			OhLohCrawlerProjectService projectCrawlerConfigService) {
		this.projectCrawlerConfigService = projectCrawlerConfigService;
	}

	public OhLohCrawlerLicenseService getLicenseCrawlerConfigService() {
		return licenseCrawlerConfigService;
	}

	public void setLicenseCrawlerConfigService(
			OhLohCrawlerLicenseService licenseCrawlerConfigService) {
		this.licenseCrawlerConfigService = licenseCrawlerConfigService;
	}

	public OhLohLicenseService getOhLohLicenseService() {
		return ohLohLicenseService;
	}

	public void setOhLohLicenseService(OhLohLicenseService ohLohLicenseService) {
		this.ohLohLicenseService = ohLohLicenseService;
	}

	public OhLohActivityFactService getOhLohActivityFactService() {
		return ohLohActivityFactService;
	}
	
	public void setOhLohActivityFactService(
			OhLohActivityFactService ohLohActivityFactService) {
		this.ohLohActivityFactService = ohLohActivityFactService;
	}
	
	public OhLohSizeFactService getOhLohSizeFactService() {
		return ohLohSizeFactService;
	}
	
	public void setOhLohSizeFactService(
			OhLohSizeFactService ohLohSizeFactService) {
		this.ohLohSizeFactService = ohLohSizeFactService;
	}
	
	public OhLohProjectCrawler() {
	}
	
	protected void downloadLanguages() throws Exception {
		logger.info(String.format("Baixando todas as linguagens de programa��o"));
		
		OhLohBaseRequest request = new OhLohBaseRequest();
		
		Integer totalPages = 0;
		Integer page = 1;
		
		OhLohCrawlerLanguageEntity config = getLanguageCrawlerConfigService().findCrawlerConfig();
		if (config == null) {
			config = new OhLohCrawlerLanguageEntity();
			config.setCurrentPage(page);
		} else {
			if (config.getCurrentPage() != null) {
				page = config.getCurrentPage();
			}
			
			if(config.getTotalPage() != null) {
				totalPages = config.getTotalPage();
			}
		}
		
		try {
			do {
				
				//configurando requisi��o
				request.setPage(config.getCurrentPage());
				
				//efetuando requisi��o
				OhLohLanguageResponse response = getOhLohRestfulClient().getAllLanguages(request);
				logger.info(String.format("Current Language Page %d | Total Language Pages: %d | Total Language Stored: %d", page, totalPages, getOhLanguageService().countAll()));
				
				//atualizando 
				if (totalPages <= 0 && response.getItemsAvailable() != null && response.getItemsReturned() != null) {
					totalPages = response.getItemsAvailable() / response.getItemsReturned();
					
					if (response.getItemsAvailable() % response.getItemsReturned() > 0) {
						totalPages++;
					}
					
					config.setTotalPage(totalPages);
				}
				
				if (response != null && OhLohLanguageResponse.SUCCESS.equals(response.getStatus())) {
					OhLohLanguageResult result = response.getResult();
					
					if  (result != null) {
						List<OhLohLanguageDTO> languageDTOList = result.getOhLohLanguages();
						if (languageDTOList != null && ! languageDTOList.isEmpty()) {
							for (OhLohLanguageDTO languageDTO : languageDTOList) {
								if (getOhLanguageService().findById(languageDTO.getId()) == null) {
									getOhLanguageService().process(languageDTO);
								}
							}
						}
					}
				} else {
					break;
				}
				
				if (response.getItemsReturned() > 0) {
					page++;
				}
				
				config.setCurrentPage(page);
				
			} while (page < totalPages);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			getLanguageCrawlerConfigService().save(config);
		}
	}
	
	private void downloadEnlistments(OhLohProjectEntity project) throws Exception {
		OhLohBaseRequest request = new OhLohBaseRequest();
		
		Integer totalPages = 0;
		Integer page = 1;
		
		OhLohCrawlerEnlistmentEntity config = getEnlistmentCrawlerConfigService().findCrawlerConfig();
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
		
		if (config.getOhLohProject() == null || ! config.getOhLohProject().equals(project)) {
			config.setOhLohProject(project);
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
					
					logger.info(String.format("Baixando enlistments para o projeto %d - p�gina %d", project.getId(), request.getPage()));
					
					OhLohEnlistmentResponse response = getOhLohRestfulClient().getAllProjectEnlistments(project.getId().toString(), request);
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
						List<OhLohEnlistmentDTO> ohLohEnlistmentDTOs = response.getResult().getOhLohEnlistments();
						if (ohLohEnlistmentDTOs != null && ! ohLohEnlistmentDTOs.isEmpty()) {
							for (OhLohEnlistmentDTO enlistment : ohLohEnlistmentDTOs) {
								getOhLohEnlistmentService().process(enlistment);
							}
						}
					}
					

					if (response.getItemsReturned() > 0) {
						page++;
					}
					
					config.setOhLohProject(project);
					config.setCurrentPage(page);
					config.setItemsAvailable(response.getItemsAvailable());
					config.setItemsPerPage(response.getItemsReturned());
					
					
				} while (page < totalPages);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				getEnlistmentCrawlerConfigService().save(config);
			}
		}
	}
	
	/**
	 * Deixa o processo de baixar dados mais lento, por persistir um volume
	 * muito grande de informa��es por projeto
	 * @param project
	 * @throws Exception
	 */
	protected void downloadActivityFacts(OhLohProjectEntity project) throws Exception {
		if (project != null && project.getId() != null) {
			OhLohActivityFactResponse response = getOhLohRestfulClient().getLatestProjectActivityFacts(project.getId().toString());
			if (OhLohActivityFactResponse.SUCCESS.equals(response.getStatus())) {
				OhLohActivityFactResult result = response.getResult();
				if (result != null && result.getOhLohActivityFacts() != null) {
					for (OhLohActivityFactDTO activityFactDTO : result.getOhLohActivityFacts()) {
						OhLohProjectDTO projectDTO = new OhLohProjectDTO();
						projectDTO.setId(project.getId());
						
						activityFactDTO.setProjectId(project.getId());
						activityFactDTO.setOhlohProject(projectDTO);
						
						getOhLohActivityFactService().process(activityFactDTO);
					}
				}
			}
		}
	}
	
	/**
	 * Deixa o processo de baixar dados mais lento, por persistir um volume
	 * muito grande de informa��es por projeto
	 * @param project
	 * @throws Exception
	 */
	protected void downloadSizeFacts(OhLohProjectEntity project) throws Exception {
		if (project != null && project.getId() != null) {
			OhLohSizeFactResponse response = getOhLohRestfulClient().getLatestSizeFackByProject(project.getId().toString());
			if (OhLohSizeFactResponse.SUCCESS.equals(response.getStatus())) {
				OhLohSizeFactResult result = response.getResult();
				if (result != null && result.getOhLohSizeFacts() != null) {
					for (OhLohSizeFactDTO sizeFactDTO : result.getOhLohSizeFacts()) {
						OhLohProjectDTO projectDTO = new OhLohProjectDTO();
						projectDTO.setId(project.getId());
						
						sizeFactDTO.setProjectId(project.getId());
						sizeFactDTO.setOhlohProject(projectDTO);
						
						getOhLohSizeFactService().process(sizeFactDTO);
					}
				}
			}
		}
	}
	
	@Scheduled(cron="0 0 0 * * *")
	public void run() throws Exception {
		
		downloadLanguages();
		
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
				logger.info(String.format("Total Pages: %d | Total Projects: %d", totalPages, ohLohProjectService.countAll()));
				
				if (totalPages <= 0 && response.getItemsAvailable() != null && response.getItemsReturned() != null) {
					totalPages = response.getItemsAvailable() / response.getItemsReturned();
					if (response.getItemsAvailable() % response.getItemsReturned() > 0) {
						totalPages++;
					}
						
					
					config.setTotalPage(totalPages);
				}
				
				//Servidor retornou com sucesso. Armazene os dados dos projetos
				if (OhLohProjectResponse.SUCCESS.equals(response.getStatus())) {
					List<OhLohProjectDTO> ohLohProjectDTOs = response.getResult().getOhLohProjects();
					if (ohLohProjectDTOs != null && ! ohLohProjectDTOs.isEmpty()) {
						logger.info(String.format("Page: %d | Projects: %d", page, ohLohProjectDTOs.size()));
						
						//iterar cada projeto da lista retornada
						for (OhLohProjectDTO project : ohLohProjectDTOs) {
							if (ohLohProjectService.findById(project.getId()) == null) {
								//armazenando projeto
								OhLohProjectEntity ohLohProject = ohLohProjectService.process(project);								
								
								config.setOhLohProject(ohLohProject);
								config.setCurrentPage(page);
								config.setItemsAvailable(response.getItemsAvailable());
								config.setItemsPerPage(response.getItemsReturned());
								getProjectCrawlerConfigService().save(config);
								
								downloadEnlistments(ohLohProject);
								
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
				config.setOhLohProject(null);
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
		
	}

}
