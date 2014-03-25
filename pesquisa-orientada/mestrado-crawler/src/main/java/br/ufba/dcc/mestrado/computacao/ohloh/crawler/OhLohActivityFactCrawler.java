package br.ufba.dcc.mestrado.computacao.ohloh.crawler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.activityfact.OhLohActivityFactDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.activityfact.OhLohActivityFactResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohActivityFactResponse;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohActivityFactService;

@Component
public class OhLohActivityFactCrawler {
	
	@Autowired
	private OhLohActivityFactService ohLohActivityFactService;
	
	@Autowired
	private OhLohRestfulClient ohLohRestfulClient;
	
	public OhLohRestfulClient getRestfulClient() {
		return ohLohRestfulClient;
	}
	
	public void setOhLohRestfulClient(OhLohRestfulClient ohLohRestfulClient) {
		this.ohLohRestfulClient = ohLohRestfulClient;
	}
	
	public OhLohActivityFactService getActivityFactService() {
		return ohLohActivityFactService;
	}
	
	public void setOhLohActivityFactService(OhLohActivityFactService ohLohActivityFactService) {
		this.ohLohActivityFactService = ohLohActivityFactService;
	}
	
	/**
	 * Deixa o processo de baixar dados mais lento, por persistir um volume
	 * muito grande de informa��es por projeto
	 * @param project
	 * @throws Exception
	 */
	public void downloadActivityFacts(OhLohProjectEntity project) throws Exception {
		if (project != null && project.getId() != null) {
			OhLohActivityFactResponse response = getRestfulClient().getLatestProjectActivityFacts(project.getId().toString());
			if (OhLohActivityFactResponse.SUCCESS.equals(response.getStatus())) {
				OhLohActivityFactResult result = response.getResult();
				if (result != null && result.getActivityFacts() != null) {
					for (OhLohActivityFactDTO activityFactDTO : result.getActivityFacts()) {
						OhLohProjectDTO projectDTO = new OhLohProjectDTO();
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
	public void downloadActivityFacts(List<OhLohProjectEntity> projectList) throws Exception {
		if (projectList != null) {
			for (OhLohProjectEntity project : projectList) {
				downloadActivityFacts(project);
			}
		}
	}

}
