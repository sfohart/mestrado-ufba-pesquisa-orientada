package br.ufba.dcc.mestrado.computacao.openhub.data.stack;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectDTO;

@XmlRootElement (name = OpenHubStackEntryDTO.NODE_NAME)
public class OpenHubStackEntryDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2406930252998297315L;

	public final static String NODE_NAME = "stack_entry";
	
	private String id;

	private Long stackId;

	private Long projectId;

	private Date createdAt;

	private OpenHubProjectDTO project;

	@XmlElement(name = "stack_id")
	public Long getStackId() {
		return stackId;
	}

	public void setStackId(Long stackId) {
		this.stackId = stackId;
	}

	@XmlElement(name = "project_id")
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	@XmlElement(name = "created_at")
	@XmlSchemaType(name = "date")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@XmlElement(name = "project")
	public OpenHubProjectDTO getProject() {
		return project;
	}

	public void setProject(OpenHubProjectDTO project) {
		this.project = project;
	}
	
	@XmlID
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

}
