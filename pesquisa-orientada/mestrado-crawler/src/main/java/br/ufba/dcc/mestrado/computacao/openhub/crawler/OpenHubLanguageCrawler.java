package br.ufba.dcc.mestrado.computacao.openhub.crawler;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerLanguageEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.language.OpenHubLanguageDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.language.OpenHubLanguageResult;
import br.ufba.dcc.mestrado.computacao.openhub.restful.client.OpenHubRestfulClientImpl;
import br.ufba.dcc.mestrado.computacao.openhub.restful.request.OpenHubBaseRequest;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubLanguageResponse;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubCrawlerLanguageService;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubLanguageService;

@Component
public class OpenHubLanguageCrawler {
	
	private static final Logger logger = Logger.getLogger(OpenHubLanguageCrawler.class.getName());
	
	@Autowired
	private OpenHubCrawlerLanguageService languageCrawlerConfigService;

	@Autowired
	private OpenHubLanguageService ohLanguageService;
	
	@Autowired
	private OpenHubRestfulClientImpl ohLohRestfulClient;

	public OpenHubCrawlerLanguageService getLanguageCrawlerConfigService() {
		return languageCrawlerConfigService;
	}

	public void setLanguageCrawlerConfigService(
			OpenHubCrawlerLanguageService languageCrawlerConfigService) {
		this.languageCrawlerConfigService = languageCrawlerConfigService;
	}

	public OpenHubLanguageService getOhLanguageService() {
		return ohLanguageService;
	}

	public void setOhLanguageService(OpenHubLanguageService ohLanguageService) {
		this.ohLanguageService = ohLanguageService;
	}

	public OpenHubRestfulClientImpl getRestfulClient() {
		return ohLohRestfulClient;
	}

	public void setOhLohRestfulClient(OpenHubRestfulClientImpl ohLohRestfulClient) {
		this.ohLohRestfulClient = ohLohRestfulClient;
	}
	
	public void downloadLanguages() throws Exception {
		logger.info(String.format("Baixando todas as linguagens de programação"));
		
		OpenHubBaseRequest request = new OpenHubBaseRequest();
		
		Integer totalPages = 0;
		Integer page = 1;
		
		OpenHubCrawlerLanguageEntity config = getLanguageCrawlerConfigService().findCrawlerConfig();
		if (config == null) {
			config = new OpenHubCrawlerLanguageEntity();
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
				
				//configurando requisiï¿½ï¿½o
				request.setPage(config.getCurrentPage());
				
				//efetuando requisiï¿½ï¿½o
				OpenHubLanguageResponse response = getRestfulClient().getAllLanguages(request);
				logger.info(String.format("Current Language Page %d | Total Language Pages: %d | Total Language Stored: %d", page, totalPages, getOhLanguageService().countAll()));
				
				//atualizando 
				if (totalPages <= 0 && response.getItemsAvailable() != null && response.getItemsReturned() != null) {
					totalPages = response.getItemsAvailable() / response.getItemsReturned();
					
					if (response.getItemsAvailable() % response.getItemsReturned() > 0) {
						totalPages++;
					}
					
					config.setTotalPage(totalPages);
				}
				
				if (response != null && OpenHubLanguageResponse.SUCCESS.equals(response.getStatus())) {
					OpenHubLanguageResult result = response.getResult();
					
					if  (result != null) {
						List<OpenHubLanguageDTO> languageDTOList = result.getLanguages();
						if (languageDTOList != null && ! languageDTOList.isEmpty()) {
							for (OpenHubLanguageDTO languageDTO : languageDTOList) {
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
	
}
