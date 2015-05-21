package br.ufba.dcc.mestrado.computacao.openhub.data.organization;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubOrganizationDTO.NODE_NAME)
public class OpenHubOrganizationDTO implements OpenHubResultDTO {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2456133066712617970L;
	
	public final static String NODE_NAME = "organization";
	
	private String name;
	
	private String url;
	
	private String htmlURL;
	
	private Date createdAt;

	private Date updatedAt;
	
	private String description;

	private String homepageURL;
	
	private String urlName;
	
	private String type;
	
	private String mediumLogoURL;

	private String smallLogoURL;
	
	private OpenHubInfographicDettailsDTO infographicDetails;
	
	private List<OpenHubPortifolioProjectDTO> portifolioProjects;
	
	private List<OpenHubOutsideProjectDTO> outsideProjects;
	
	private List<OpenHubOutsideCommittterDTO> outsideCommitters;
	
	private List<OpenHubAffiliatedCommitterDTO> affiliatedCommitters;

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlElement(name = "html_url")
	public String getHtmlURL() {
		return htmlURL;
	}

	public void setHtmlURL(String htmlURL) {
		this.htmlURL = htmlURL;
	}

	@XmlElement(name = "created_at")
	@XmlSchemaType(name = "date")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@XmlElement(name = "updated_at")
	@XmlSchemaType(name = "date")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "homepage_url")
	public String getHomepageURL() {
		return homepageURL;
	}

	public void setHomepageURL(String homepageURL) {
		this.homepageURL = homepageURL;
	}

	@XmlElement(name = "url_name")
	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	@XmlElement(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(name = "medium_logo_url")
	public String getMediumLogoURL() {
		return mediumLogoURL;
	}

	public void setMediumLogoURL(String mediumLogoURL) {
		this.mediumLogoURL = mediumLogoURL;
	}

	@XmlElement(name = "small_logo_url")
	public String getSmallLogoURL() {
		return smallLogoURL;
	}

	public void setSmallLogoURL(String smallLogoURL) {
		this.smallLogoURL = smallLogoURL;
	}
	
	@XmlElement(name = "inforgraphic_details")
	public OpenHubInfographicDettailsDTO getInfographicDetails() {
		return infographicDetails;
	}

	public void setInfographicDetails(OpenHubInfographicDettailsDTO infographicDetails) {
		this.infographicDetails = infographicDetails;
	}

	@XmlElement(name = "portfolio_projects")
	public List<OpenHubPortifolioProjectDTO> getPortifolioProjects() {
		return portifolioProjects;
	}

	public void setPortifolioProjects(
			List<OpenHubPortifolioProjectDTO> portifolioProjects) {
		this.portifolioProjects = portifolioProjects;
	}

	@XmlElement(name = "outside_projects")
	public List<OpenHubOutsideProjectDTO> getOutsideProjects() {
		return outsideProjects;
	}

	public void setOutsideProjects(List<OpenHubOutsideProjectDTO> outsideProjects) {
		this.outsideProjects = outsideProjects;
	}

	@XmlElement(name = "outside_committers")
	public List<OpenHubOutsideCommittterDTO> getOutsideCommitters() {
		return outsideCommitters;
	}

	public void setOutsideCommitters(
			List<OpenHubOutsideCommittterDTO> outsideCommitters) {
		this.outsideCommitters = outsideCommitters;
	}

	@XmlElement(name = "affiliated_committers")
	public List<OpenHubAffiliatedCommitterDTO> getAffiliatedCommitters() {
		return affiliatedCommitters;
	}

	public void setAffiliatedCommitters(
			List<OpenHubAffiliatedCommitterDTO> affiliatedCommitters) {
		this.affiliatedCommitters = affiliatedCommitters;
	}
	
	
	
}
