package br.ufba.dcc.mestrado.computacao.ohloh.crawler;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerLanguageEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageResult;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohLanguageResponse;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerLanguageService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLanguageService;

@Component
public class OhLohLanguageCrawler {
	
	private static final Logger logger = Logger.getLogger(OhLohLanguageCrawler.class.getName());
	
	@Autowired
	private OhLohCrawlerLanguageService languageCrawlerConfigService;

	@Autowired
	private OhLohLanguageService ohLanguageService;
	
	@Autowired
	private OhLohRestfulClient ohLohRestfulClient;

	public OhLohCrawlerLanguageService getLanguageCrawlerConfigService() {
		return languageCrawlerConfigService;
	}

	public void setLanguageCrawlerConfigService(
			OhLohCrawlerLanguageService languageCrawlerConfigService) {
		this.languageCrawlerConfigService = languageCrawlerConfigService;
	}

	public OhLohLanguageService getOhLanguageService() {
		return ohLanguageService;
	}

	public void setOhLanguageService(OhLohLanguageService ohLanguageService) {
		this.ohLanguageService = ohLanguageService;
	}

	public OhLohRestfulClient getOhLohRestfulClient() {
		return ohLohRestfulClient;
	}

	public void setOhLohRestfulClient(OhLohRestfulClient ohLohRestfulClient) {
		this.ohLohRestfulClient = ohLohRestfulClient;
	}
	
	public void downloadLanguages() throws Exception {
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
	
}
