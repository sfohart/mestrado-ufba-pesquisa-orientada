package br.ufba.dcc.mestrado.computacao.ohloh.crawler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.sizefact.OhLohSizeFactDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.sizefact.OhLohSizeFactResult;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohSizeFactResponse;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohSizeFactService;

@Component
public class OhLohSizeFactCrawler {

	@Autowired
	private OhLohSizeFactService ohLohSizeFactService;
	
	@Autowired
	private OhLohRestfulClient ohLohRestfulClient;
	
	public OhLohRestfulClient getOhLohRestfulClient() {
		return ohLohRestfulClient;
	}
	public void setOhLohRestfulClient(OhLohRestfulClient ohLohRestfulClient) {
		this.ohLohRestfulClient = ohLohRestfulClient;
	}
	
	public OhLohSizeFactService getOhLohSizeFactService() {
		return ohLohSizeFactService;
	}
	
	public void setOhLohSizeFactService(OhLohSizeFactService ohLohSizeFactService) {
		this.ohLohSizeFactService = ohLohSizeFactService;
	}
	
	/**
	 * Deixa o processo de baixar dados mais lento, por persistir um volume
	 * muito grande de informa��es por projeto
	 * @param project
	 * @throws Exception
	 */
	public void downloadSizeFacts(OhLohProjectEntity project) throws Exception {
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
	
	/**
	 * 
	 * @param projectList
	 * @throws Exception
	 */
	public void downloadSizeFacts(List<OhLohProjectEntity> projectList) throws Exception {
		if (projectList != null) {
			for (OhLohProjectEntity project : projectList) {
				downloadSizeFacts(project);
			}
		}
	}
	
}
