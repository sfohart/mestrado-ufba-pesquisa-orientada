package br.ufba.dcc.mestrado.computacao.openhub.data.sizefact;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectDTO;

@XmlRootElement(name = OpenHubSizeFactDTO.NODE_NAME)
public class OpenHubSizeFactDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5308357200941776405L;

	public final static String NODE_NAME = "size_fact";
	
	private String id;

	private Date month;

	private Long code;

	private Long comments;

	private Long blanks;

	private Double commentRatio;

	private String commits;

	private Long manMonths;
	
	private Long projectId;
	
	private OpenHubProjectDTO project;

	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "month")
	@XmlSchemaType(name = "date")
	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	@XmlElement(name = "code")
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	@XmlElement(name = "comments")
	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	@XmlElement(name = "blanks")
	public Long getBlanks() {
		return blanks;
	}

	public void setBlanks(Long blanks) {
		this.blanks = blanks;
	}

	@XmlElement(name = "comment_ratio")
	public Double getCommentRatio() {
		return commentRatio;
	}

	public void setCommentRatio(Double commentRatio) {
		this.commentRatio = commentRatio;
	}

	@XmlElement(name = "commits")
	public String getCommits() {
		return commits;
	}

	public void setCommits(String commits) {
		this.commits = commits;
	}

	@XmlElement(name = "man_months")
	public Long getManMonths() {
		return manMonths;
	}

	public void setManMonths(Long manMonths) {
		this.manMonths = manMonths;
	}

	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public OpenHubProjectDTO getProject() {
		return project;
	}
	
	public void setProject(OpenHubProjectDTO project) {
		this.project = project;
	}
	
	

}
