package br.ufba.dcc.mestrado.computacao.openhub.data.organization;

import java.sql.Timestamp;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601SqlTimestampConverter;

@XStreamAlias(OpenHubOrganizationDTO.NODE_NAME)
public class OpenHubOrganizationDTO implements OpenHubResultDTO {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2456133066712617970L;
	
	public final static String NODE_NAME = "organization";
	
	private String name;
	
	private String url;
	
	@XStreamAlias("html_url")
	private String htmlURL;
	
	@XStreamConverter(value = ISO8601SqlTimestampConverter.class)
	@XStreamAlias("created_at")
	private Timestamp createdAt;

	@XStreamConverter(value = ISO8601SqlTimestampConverter.class)
	@XStreamAlias("updated_at")
	private Timestamp updatedAt;
	
	private String description;

	@XStreamAlias("homepage_url")
	private String homepageURL;
	
	@XStreamAlias("url_name")
	private String urlName;
	
	private String type;
	
	@XStreamAlias("medium_logo_url")
	private String mediumLogoURL;

	@XStreamAlias("small_logo_url")
	private String smallLogoURL;
	
	@XStreamAlias("projects_count")
	private Long projectsCount;
		
	@XStreamAlias("affiliated_committers")
	private Long affiliatedCommitters;	
	
	@XStreamAlias("infographic_details")
	private OpenHubInfographicDettailsDTO infographicDetails;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHtmlURL() {
		return htmlURL;
	}

	public void setHtmlURL(String htmlURL) {
		this.htmlURL = htmlURL;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHomepageURL() {
		return homepageURL;
	}

	public void setHomepageURL(String homepageURL) {
		this.homepageURL = homepageURL;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMediumLogoURL() {
		return mediumLogoURL;
	}

	public void setMediumLogoURL(String mediumLogoURL) {
		this.mediumLogoURL = mediumLogoURL;
	}

	public String getSmallLogoURL() {
		return smallLogoURL;
	}

	public void setSmallLogoURL(String smallLogoURL) {
		this.smallLogoURL = smallLogoURL;
	}

	public Long getProjectsCount() {
		return projectsCount;
	}

	public void setProjectsCount(Long projectsCount) {
		this.projectsCount = projectsCount;
	}

	public Long getAffiliatedCommitters() {
		return affiliatedCommitters;
	}

	public void setAffiliatedCommitters(Long affiliatedCommitters) {
		this.affiliatedCommitters = affiliatedCommitters;
	}

	public OpenHubInfographicDettailsDTO getInfographicDetails() {
		return infographicDetails;
	}

	public void setInfographicDetails(OpenHubInfographicDettailsDTO infographicDetails) {
		this.infographicDetails = infographicDetails;
	}
	
	
}
