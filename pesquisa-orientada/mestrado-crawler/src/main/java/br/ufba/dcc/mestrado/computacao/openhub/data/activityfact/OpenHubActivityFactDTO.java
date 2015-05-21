package br.ufba.dcc.mestrado.computacao.openhub.data.activityfact;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectDTO;

@XmlRootElement(name = OpenHubActivityFactDTO.NODE_NAME)
public class OpenHubActivityFactDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1220433997459663718L;

	public final static String NODE_NAME = "activity_fact";

	private Date month;

	private Long codeAdded;

	private Long codeRemoved;

	private Long commentsAdded;

	private Long commentsRemoved;

	private Long blanksAdded;

	private Long blanksRemoved;

	private Long commits;

	private Long contributors;
	
	private Long projectId;
	
	private OpenHubProjectDTO project;

	@XmlElement(name = "month")
	@XmlSchemaType(name = "date")
	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	@XmlElement(name = "code_added")
	public Long getCodeAdded() {
		return codeAdded;
	}

	public void setCodeAdded(Long codeAdded) {
		this.codeAdded = codeAdded;
	}

	@XmlElement(name = "code_removed")
	public Long getCodeRemoved() {
		return codeRemoved;
	}

	public void setCodeRemoved(Long codeRemoved) {
		this.codeRemoved = codeRemoved;
	}

	@XmlElement(name = "comments_added")
	public Long getCommentsAdded() {
		return commentsAdded;
	}

	public void setCommentsAdded(Long commentsAdded) {
		this.commentsAdded = commentsAdded;
	}

	@XmlElement(name = "comments_removed")
	public Long getCommentsRemoved() {
		return commentsRemoved;
	}

	public void setCommentsRemoved(Long commentsRemoved) {
		this.commentsRemoved = commentsRemoved;
	}

	@XmlElement(name = "blanks_added")
	public Long getBlanksAdded() {
		return blanksAdded;
	}

	public void setBlanksAdded(Long blanksAdded) {
		this.blanksAdded = blanksAdded;
	}

	@XmlElement(name = "blanks_removed")
	public Long getBlanksRemoved() {
		return blanksRemoved;
	}

	public void setBlanksRemoved(Long blanksRemoved) {
		this.blanksRemoved = blanksRemoved;
	}

	@XmlElement(name = "commits")
	public Long getCommits() {
		return commits;
	}

	public void setCommits(Long commits) {
		this.commits = commits;
	}

	@XmlElement(name = "contributors")
	public Long getContributors() {
		return contributors;
	}

	public void setContributors(Long contributors) {
		this.contributors = contributors;
	}

	@XmlElement(name = "project_id")
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	@XmlElement(name = "project")
	public OpenHubProjectDTO getProject() {
		return project;
	}
	
	public void setProject(OpenHubProjectDTO project) {
		this.project = project;
	}

	
	
}
