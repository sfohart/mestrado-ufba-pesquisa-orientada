package br.ufba.dcc.mestrado.computacao.openhub.data.enlistment;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias(OpenHubEnlistmentDTO.NODE_NAME)
public class OpenHubEnlistmentDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -72467167025120985L;

	public final static String NODE_NAME = "enlistment";

	@XStreamAsAttribute	
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long id;
	
	@XStreamAlias("project_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long projectId;

	@XStreamAlias("repository_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long repositoryId;

	@XStreamAlias("repository")
	private OpenHubRepositoryDTO repository;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

	public OpenHubRepositoryDTO getRepository() {
		return repository;
	}
	
	public void setRepository(OpenHubRepositoryDTO repository) {
		this.repository = repository;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

}
