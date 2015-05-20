package br.ufba.dcc.mestrado.computacao.openhub.data.enlistment;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubEnlistmentDTO.NODE_NAME)
public class OpenHubEnlistmentDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -72467167025120985L;

	public final static String NODE_NAME = "enlistment";

	private String id;
	
	private Long projectId;

	private Long repositoryId;

	private OpenHubRepositoryDTO repository;

	@XmlElement(name = "project_id")
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	@XmlElement(name = "repository_id")
	public Long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

	@XmlElement(name = "repository")
	public OpenHubRepositoryDTO getRepository() {
		return repository;
	}
	
	public void setRepository(OpenHubRepositoryDTO repository) {
		this.repository = repository;
	}

	@XmlID
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

}
