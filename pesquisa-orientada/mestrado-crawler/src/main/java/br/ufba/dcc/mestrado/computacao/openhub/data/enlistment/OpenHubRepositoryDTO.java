package br.ufba.dcc.mestrado.computacao.openhub.data.enlistment;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.enlistment.OpenHubRepositoryTypeEnum;
import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubRepositoryDTO.NODE_NAME)
public class OpenHubRepositoryDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3900364356015056852L;


	public final static String NODE_NAME = "repository";
	
	private String id;

	private OpenHubRepositoryTypeEnum type;

	private String url;

	private String moduleName;

	private String username;

	private String password;

	private Date loggedAt;

	private Long commits;

	
	private String jobStatus;

	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public OpenHubRepositoryTypeEnum getType() {
		return type;
	}

	public void setType(OpenHubRepositoryTypeEnum type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlElement(name = "module_name")
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement(name = "logged_at")
	@XmlSchemaType(name = "date")
	public Date getLoggedAt() {
		return loggedAt;
	}

	public void setLoggedAt(Date loggedAt) {
		this.loggedAt = loggedAt;
	}

	public Long getCommits() {
		return commits;
	}

	public void setCommits(Long commits) {
		this.commits = commits;
	}

	@XmlElement(name = "ohloh_job_status")
	public String getJobStatus() {
		return jobStatus;
	}
	
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

}
