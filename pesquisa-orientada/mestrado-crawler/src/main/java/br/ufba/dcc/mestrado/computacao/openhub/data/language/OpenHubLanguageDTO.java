package br.ufba.dcc.mestrado.computacao.openhub.data.language;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubLanguageDTO.NODE_NAME)
public class OpenHubLanguageDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2035131004549723264L;

	public final static String NODE_NAME = "language";
	
	
	private String id;
	
	private String name;
	
	private String niceName;
	
	private String category;
	
	private Long code;
	
	private Long comments;
	
	private Long blanks;
	
	private Double commentRatio;
	
	private Long projects;
	
	private Long contributors;
	
	private Long commits;
	
	public OpenHubLanguageDTO() {
		super();
	}

	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "nice_name")
	public String getNiceName() {
		return niceName;
	}

	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

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

	public Long getProjects() {
		return projects;
	}

	public void setProjects(Long projects) {
		this.projects = projects;
	}

	public Long getContributors() {
		return contributors;
	}

	public void setContributors(Long contributors) {
		this.contributors = contributors;
	}

	public Long getCommits() {
		return commits;
	}

	public void setCommits(Long commits) {
		this.commits = commits;
	}
	

}
