package br.ufba.dcc.mestrado.computacao.entities.openhub.core.enlistment;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

@Entity
@Table(name = OpenHubRepositoryEntity.NODE_NAME)
public class OpenHubRepositoryEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3900364356015056852L;


	public final static String NODE_NAME = "repository";

	@Id
	private Long id;
	
	@Column(name = "type")
	private OpenHubRepositoryTypeEnum type;

	@Column(name = "url")
	private String url;

	@Column(name = "module_name")
	private String moduleName;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "logged_at")
	private Timestamp loggedAt;

	@Column(name = "commits")
	private Long commits;

	@Column(name = "ohloh_job_status")
	private String jobStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Timestamp getLoggedAt() {
		return loggedAt;
	}

	public void setLoggedAt(Timestamp loggedAt) {
		this.loggedAt = loggedAt;
	}

	public Long getCommits() {
		return commits;
	}

	public void setCommits(Long commits) {
		this.commits = commits;
	}

	public String getJobStatus() {
		return jobStatus;
	}
	
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

}
