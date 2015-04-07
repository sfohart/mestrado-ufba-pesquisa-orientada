package br.ufba.dcc.mestrado.computacao.openhub.crawler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.activityfact.OpenHubActivityFactDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.activityfact.OpenHubActivityFactResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectDTO;
import br.ufba.dcc.mestrado.computacao.openhub.restful.client.OpenHubRestfulClientImpl;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubActivityFactResponse;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubActivityFactService;

@Component
public class OpenHubActivityFactCrawler {
	
	@Autowired
	private OpenHubActivityFactService openHubActivityFactService;
	
	@Autowired
	private OpenHubRestfulClientImpl ohLohRestfulClient;
	
	public OpenHubRestfulClientImpl getRestfulClient() {
		return ohLohRestfulClient;
	}
	
	public void setOhLohRestfulClient(OpenHubRestfulClientImpl ohLohRestfulClient) {
		this.ohLohRestfulClient = ohLohRestfulClient;
	}
	
	public OpenHubActivityFactService getActivityFactService() {
		return openHubActivityFactService;
	}
	
	public void setOhLohActivityFactService(OpenHubActivityFactService openHubActivityFactService) {
		this.openHubActivityFactService = openHubActivityFactService;
	}
	
	/**
	 * Deixa o processo de baixar dados mais lento, por persistir um volume
	 * muito grande de informa��es por projeto
	 * @param project
	 * @throws Exception
	 */
	public void downloadActivityFacts(OpenHubProjectEntity project) throws Exception {
		if (project != null && project.getId() != null) {
			OpenHubActivityFactResponse response = getRestfulClient().getLatestProjectActivityFacts(project.getId().toString());
			if (OpenHubActivityFactResponse.SUCCESS.equals(response.getStatus())) {
				OpenHubActivityFactResult result = response.getResult();
				if (result != null && result.getActivityFacts() != null) {
					for (OpenHubActivityFactDTO activityFactDTO : result.getActivityFacts()) {
						OpenHubProjectDTO projectDTO = new OpenHubProjectDTO();
						projectDTO.setId(project.getId());
						
						activityFactDTO.setProjectId(project.getId());
						activityFactDTO.setProject(projectDTO);
						
						getActivityFactService().process(activityFactDTO);
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
	public void downloadActivityFacts(List<OpenHubProjectEntity> projectList) throws Exception {
		if (projectList != null) {
			for (OpenHubProjectEntity project : projectList) {
				downloadActivityFacts(project);
			}
		}
	}

}
