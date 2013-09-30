package br.ufba.dcc.mestrado.computacao.ohloh.data.enlistment;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias(OhLohEnlistmentDTO.NODE_NAME)
public class OhLohEnlistmentDTO implements OhLohResultDTO {

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
	private OhLohRepositoryDTO ohLohRepositoryDTO;

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

	public OhLohRepositoryDTO getOhLohRepository() {
		return ohLohRepositoryDTO;
	}

	public void setOhLohRepository(OhLohRepositoryDTO ohLohRepositoryDTO) {
		this.ohLohRepositoryDTO = ohLohRepositoryDTO;
	}

}
