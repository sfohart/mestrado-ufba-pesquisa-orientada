package br.ufba.dcc.mestrado.computacao.openhub.data.project;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.analysis.OpenHubAnalysisDTO;

@XmlRootElement(name = OpenHubProjectDTO.NODE_NAME)
public class OpenHubProjectDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9128774489845120800L;

	public final static String NODE_NAME = "project";

	private String id;

	private String name;
	private String url;
	private String htmlURL;

	private Date createdAt;
	private Date updatedAt;

	private String description;
	private String homepageURL;
	private String downloadURL;
	private String urlName;
	private String mediumLogoURL;
	private String smallLogoURL;

	private Double averageRating;
	
	private Long userCount;
	private Long ratingCount;
	private Long reviewCount;
	private Long analysisId;

	private OpenHubAnalysisDTO analysis;

	private List<OpenHubLicenseDTO> licenses;
	private List<OpenHubLinkDTO> links;
	private List<OpenHubTagDTO> tags;
	
	private OpenHubProjectActivityIndexDTO projectActivityIndex;

	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
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

	@XmlElement
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

	@XmlElement(name = "download_url")
	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	@XmlElement(name = "url_name")
	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
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

	@XmlElement(name = "average_rating")
	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	@XmlElement(name = "user_count")
	public Long getUserCount() {
		return userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

	@XmlElement(name = "rating_count")
	public Long getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Long ratingCount) {
		this.ratingCount = ratingCount;
	}

	@XmlElement(name = "review_count")
	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		this.reviewCount = reviewCount;
	}

	@XmlElement(name = "analysis_id")
	public Long getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Long analysisId) {
		this.analysisId = analysisId;
	}

	@XmlElement(name = "analysis")
	public OpenHubAnalysisDTO getAnalysis() {
		return analysis;
	}

	public void setAnalysis(OpenHubAnalysisDTO analysis) {
		this.analysis = analysis;
	}

	@XmlElementWrapper(name = "licenses")
	@XmlElement(name = "license")
	public List<OpenHubLicenseDTO> getLicenses() {
		return licenses;
	}

	public void setLicenses(List<OpenHubLicenseDTO> licenses) {
		this.licenses = licenses;
	}

	@XmlElementWrapper(name = "links")
	@XmlElement(name = "link")
	public List<OpenHubLinkDTO> getLinks() {
		return links;
	}

	public void setLinks(List<OpenHubLinkDTO> links) {
		this.links = links;
	}

	@XmlElementWrapper(name = "tags")
	@XmlElement(name = "tag")
	public List<OpenHubTagDTO> getTags() {
		return tags;
	}

	public void setTags(List<OpenHubTagDTO> tags) {
		this.tags = tags;
	}

	@XmlElement(name = "project_activity_index")
	public OpenHubProjectActivityIndexDTO getProjectActivityIndex() {
		return projectActivityIndex;
	}

	public void setProjectActivityIndex(
			OpenHubProjectActivityIndexDTO projectActivityIndex) {
		this.projectActivityIndex = projectActivityIndex;
	}
	

	
	
}
