package br.ufba.dcc.mestrado.computacao.ohloh.crawler;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerLanguageEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerStackEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.analysis.OhLohAnalysisDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.stack.OhLohStackDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.stack.OhLohStackResult;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohLanguageResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohProjectResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohStackResponse;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerLanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerProjectRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerStackRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohAnalysisService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLanguageService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohStackService;
import br.ufba.dcc.mestrado.computacao.spring.CrawlerAppConfig;


@Component
public class OhLohCrawler {

	private static final String OHLOH_PROJECT_SORT_BY_ID = "id";

	public static Logger logger = Logger.getLogger(OhLohCrawler.class.getName());
	
	@Autowired
	private OhLohRestfulClient restfulClient;
	
	@Autowired
	private OhLohProjectService projectService;
	
	@Autowired
	private OhLohStackService stackService;
	
	@Autowired
	private OhLohAnalysisService analysisService;
	
	@Autowired
	private OhLohLanguageService languageService;
	
	@Autowired
	private OhLohCrawlerProjectRepository projectCrawlerConfigRepository;
	
	@Autowired
	private OhLohCrawlerLanguageRepository languageCrawlerConfigRepository;
	
	@Autowired
	private OhLohCrawlerStackRepository stackCrawlerConfigRepository;
	
	public OhLohRestfulClient getRestfulClient() {
		return restfulClient;
	}
	
	public void setRestfulClient(OhLohRestfulClient restfulClient) {
		this.restfulClient = restfulClient;
	}

	public OhLohProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(OhLohProjectService projectService) {
		this.projectService = projectService;
	}

	public OhLohStackService getStackService() {
		return stackService;
	}

	public void setStackService(OhLohStackService stackService) {
		this.stackService = stackService;
	}

	public OhLohAnalysisService getAnalysisService() {
		return analysisService;
	}

	public void setAnalysisService(OhLohAnalysisService analysisService) {
		this.analysisService = analysisService;
	}

	public OhLohCrawlerProjectRepository getCrawlerConfigRepository() {
		return projectCrawlerConfigRepository;
	}

	public void setCrawlerConfigRepository(
			OhLohCrawlerProjectRepository crawlerConfigRepository) {
		this.projectCrawlerConfigRepository = crawlerConfigRepository;
	}
	
	
	public OhLohLanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(OhLohLanguageService languageService) {
		this.languageService = languageService;
	}

	public OhLohCrawlerProjectRepository getProjectCrawlerConfigRepository() {
		return projectCrawlerConfigRepository;
	}

	public void setProjectCrawlerConfigRepository(
			OhLohCrawlerProjectRepository projectCrawlerConfigRepository) {
		this.projectCrawlerConfigRepository = projectCrawlerConfigRepository;
	}

	public OhLohCrawlerLanguageRepository getLanguageCrawlerConfigRepository() {
		return languageCrawlerConfigRepository;
	}

	public void setLanguageCrawlerConfigRepository(
			OhLohCrawlerLanguageRepository languageCrawlerConfigRepository) {
		this.languageCrawlerConfigRepository = languageCrawlerConfigRepository;
	}

	public OhLohCrawlerStackRepository getStackCrawlerConfigRepository() {
		return stackCrawlerConfigRepository;
	}

	public void setStackCrawlerConfigRepository(
			OhLohCrawlerStackRepository stackCrawlerConfigRepository) {
		this.stackCrawlerConfigRepository = stackCrawlerConfigRepository;
	}

	protected void downloadLanguages() throws Exception {
		logger.info(String.format("Baixando todas as linguagens de programação"));
		
		OhLohBaseRequest request = new OhLohBaseRequest();
		
		Integer totalPages = 0;
		Integer page = 1;
		
		OhLohCrawlerLanguageEntity config = languageCrawlerConfigRepository.findCrawlerConfig();
		if (config == null) {
			config = new OhLohCrawlerLanguageEntity();
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
				
				//configurando requisiï¿½ï¿½o
				request.setPage(page);
				
				//efetuando requisiï¿½ï¿½o
				OhLohLanguageResponse response = getRestfulClient().getAllLanguages(request);
				logger.info(String.format("Current Language Page %d | Total Language Pages: %d | Total Language Stored: %d", page, totalPages, getLanguageService().countAll()));
				
				//atualizando 
				if (totalPages <= 0 && response.getItemsAvailable() != null && response.getItemsReturned() != null) {
					totalPages = response.getItemsAvailable() / response.getItemsReturned();
					config.setTotalPage(totalPages);
				}
				
				if (response != null && OhLohLanguageResponse.SUCCESS.equals(response.getStatus())) {
					OhLohLanguageResult result = response.getResult();
					
					if  (result != null) {
						List<OhLohLanguageDTO> languageDTOList = result.getOhLohLanguages();
						if (languageDTOList != null && ! languageDTOList.isEmpty()) {
							for (OhLohLanguageDTO languageDTO : languageDTOList) {
								if (getLanguageService().findById(languageDTO.getId()) == null) {
									getLanguageService().process(languageDTO);
								}
							}
						}
					}
				} else {
					break;
				}
				
				page++;
				config.setCurrentPage(page);
				
			} while (page <= totalPages);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			languageCrawlerConfigRepository.save(config);
		}
		
	}

	protected void downloadAnalysis(OhLohProjectEntity project) throws Exception {
		logger.info(String.format("Baixando últimas análises para o projeto %d", project.getId()));
		
		OhLohAnalysisDTO analysisDTO = getRestfulClient().getLatestAnalysis(String.valueOf(project.getId()), null);
		if ( (analysisDTO != null) && (getAnalysisService().findById(analysisDTO.getId()) == null)) {
			logger.info(String.format("Analysis com id %d para o projeto \"%s\" sendo gravado", analysisDTO.getId(), project.getName()));
			getAnalysisService().process(analysisDTO);
		}
	}
	
	protected void downloadStack(OhLohProjectEntity project) throws Exception {
		logger.info(String.format("Baixando stack para o projeto %d", project.getId()));
		
		OhLohBaseRequest request = new OhLohBaseRequest();
		
		Integer totalPages = 0;
		Integer page = 1;
		
		OhLohCrawlerStackEntity config = stackCrawlerConfigRepository.findCrawlerConfig();
		if (config == null) {
			config = new OhLohCrawlerStackEntity();
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
			config.setCurrentPage(null);
		}
		
		if (project != null && project.getId() != null) {
			try {
				do {
					request.setPage(page);
					
					logger.info(String.format("Baixando stack para o projeto %d - página %d", project.getId(), request.getPage()));
					OhLohStackResponse response = getRestfulClient().getProjectStacks(project.getId().toString(), request);
					
					if (totalPages <= 0 
							&& response != null 
							&& response.getItemsAvailable() != null && response.getItemsAvailable() != 0 
							&& response.getItemsReturned() != null && response.getItemsReturned() != 0) {
						totalPages = response.getItemsAvailable() / response.getItemsReturned();
						config.setTotalPage(totalPages);
					}
					
					if (OhLohStackResponse.SUCCESS.equals(response.getStatus())) {
						OhLohStackResult result = response.getResult();
						if (result != null) {
							List<OhLohStackDTO> stackDTOList = result.getOhLohStacks();
							
							if (stackDTOList != null && ! stackDTOList.isEmpty()) {
								
								//armazenando cada stack obtido para esta conta na base de dados
								for (OhLohStackDTO stackDTO :  stackDTOList) {
									if (getStackService().findById(stackDTO.getId()) == null) {
										getStackService().process(stackDTO);
									}
								}
							}
						}
					}
					
					page++;
					
					config.setCurrentPage(page);
					config.setItemsAvailable(response.getItemsAvailable());
					config.setItemsPerPage(response.getItemsReturned());
					
					stackCrawlerConfigRepository.save(config);
					
				} while (page < totalPages);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				stackCrawlerConfigRepository.save(config);
			}
			
		}
	}
	
	public void run() throws Exception {
		
		//baixando as linguagens de programação
		downloadLanguages();
		
		OhLohBaseRequest request = new OhLohBaseRequest();
		Integer totalPages = 0;
		Integer currentPage = 1;
		
		request.setSort(OHLOH_PROJECT_SORT_BY_ID);
		
		OhLohCrawlerProjectEntity config = projectCrawlerConfigRepository.findCrawlerConfig();
		
		if (config != null) {
			if (config.getCurrentPage() != null) {
				currentPage = config.getCurrentPage();
			}
			
			if (config.getTotalPage() != null) {
				totalPages = config.getTotalPage();
			}
		} else {
			config = new OhLohCrawlerProjectEntity();
		}
		
		OhLohProjectEntity startProject = config.getOhLohProject();
		
		boolean projectStarted = false;
		
		try {
			do {
				
				//configurando requisicao
				request.setPage(currentPage);
				config.setCurrentPage(currentPage);
				
				//efetuando requisicao
				OhLohProjectResponse response = getRestfulClient().getAllProjects(request);
				logger.info(String.format("Total Pages: %d | Total Projects: %d", totalPages, getProjectService().countAll()));
				
				//atualizando 
				if (totalPages <= 0 
						&& response != null 
						&& response.getItemsAvailable() != null 
						&& response.getItemsReturned() != null) {
					totalPages = response.getItemsAvailable() / response.getItemsReturned();
					config.setTotalPage(totalPages);
				} 
				
				//projetos baixados com sucesso
				if (OhLohProjectResponse.SUCCESS.equals(response.getStatus())) {
					List<OhLohProjectDTO> ohLohProjectDTOs = response.getResult().getOhLohProjects();
					
					if (ohLohProjectDTOs != null && ! ohLohProjectDTOs.isEmpty()) {
						logger.info(String.format("Page: %d | Projects: %d", currentPage, ohLohProjectDTOs.size()));
						
						
						for (OhLohProjectDTO project : ohLohProjectDTOs) {
							
							if (! projectStarted) {
								if (startProject != null && startProject.getId() != null) {
									if (startProject.getId().equals(project.getId())) {
										projectStarted = true;
									} else {
										continue;
									}
								} else {
									projectStarted = true;
								}
							}
							
							OhLohProjectEntity projectEntity = getProjectService().findById(project.getId());
							
							//armazenando os projetos na base de dados
							if (projectEntity == null) {
								projectEntity = getProjectService().process(project);
							} else {
								logger.info(String.format("Projeto \"%s\" com id %d ja se encontra na base", project.getName(), project.getId()));
							}
							
							config.setOhLohProject(projectEntity);
							
							//baixando os stacks do projeto
							//downloadStack(projectEntity);
							
							//baixando as anï¿½lises do projeto
							//downloadAnalysis(projectEntity);
							
							projectCrawlerConfigRepository.save(config);
						}
						
					}
					
				} else {
					throw new IllegalStateException("OhLoh response status: " + response.getStatus() + ". " + response.getError());
				}
				
				currentPage++;
			
				config.setCurrentPage(currentPage);
				projectCrawlerConfigRepository.save(config);
				
			} while (currentPage < totalPages);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = 
		          new AnnotationConfigApplicationContext(CrawlerAppConfig.class);
		
		OhLohCrawler main = context.getBean(OhLohCrawler.class);
		
		if (main != null) {
			main.run();
		}
		
		((AbstractApplicationContext) context).registerShutdownHook();
		
	}
	
}
