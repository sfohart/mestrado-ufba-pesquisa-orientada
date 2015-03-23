package br.ufba.dcc.mestrado.computacao.openhub.crawler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.sizefact.OpenHubSizeFactDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.sizefact.OpenHubSizeFactResult;
import br.ufba.dcc.mestrado.computacao.openhub.restful.client.OpenHubRestfulClientImpl;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubSizeFactResponse;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubSizeFactService;

@Component
public class OpenHubSizeFactCrawler {

	@Autowired
	private OpenHubSizeFactService openHubSizeFactService;
	
	@Autowired
	private OpenHubRestfulClientImpl ohLohRestfulClient;
	
	public OpenHubRestfulClientImpl getRestfulClient() {
		return ohLohRestfulClient;
	}
	public void setOhLohRestfulClient(OpenHubRestfulClientImpl ohLohRestfulClient) {
		this.ohLohRestfulClient = ohLohRestfulClient;
	}
	
	public OpenHubSizeFactService getSizeFactService() {
		return openHubSizeFactService;
	}
	
	public void setOhLohSizeFactService(OpenHubSizeFactService openHubSizeFactService) {
		this.openHubSizeFactService = openHubSizeFactService;
	}
	
	/**
	 * Deixa o processo de baixar dados mais lento, por persistir um volume
	 * muito grande de informa��es por projeto
	 * @param project
	 * @throws Exception
	 */
	public void downloadSizeFacts(OpenHubProjectEntity project) throws Exception {
		if (project != null && project.getId() != null) {
			OpenHubSizeFactResponse response = getRestfulClient().getLatestSizeFackByProject(project.getId().toString());
			if (OpenHubSizeFactResponse.SUCCESS.equals(response.getStatus())) {
				OpenHubSizeFactResult result = response.getResult();
				if (result != null && result.getSizeFacts() != null) {
					for (OpenHubSizeFactDTO sizeFactDTO : result.getSizeFacts()) {
						OpenHubProjectDTO projectDTO = new OpenHubProjectDTO();
						projectDTO.setId(project.getId());
						
						sizeFactDTO.setProjectId(project.getId());
						sizeFactDTO.setProject(projectDTO);
						
						getSizeFactService().process(sizeFactDTO);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param projectList
	 * @throws Exception
	 */
	public void downloadSizeFacts(List<OpenHubProjectEntity> projectList) throws Exception {
		if (projectList != null) {
			for (OpenHubProjectEntity project : projectList) {
				downloadSizeFacts(project);
			}
		}
	}
	
}
