package br.ufba.dcc.mestrado.computacao.entities.openhub.core.project;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TermVector;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.analysis.OpenHubAnalysisEntity;

@Entity
@Table(name = OpenHubProjectEntity.NODE_NAME)
@Indexed(index = OpenHubProjectEntity.NODE_NAME) 
public class OpenHubProjectEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9128774489845120800L;

	public final static String NODE_NAME = "project";

	@Id
	@DocumentId(name = "id")
	private Long id;
	
	@Column(name = "name")
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO, termVector = TermVector.YES)
	private String name;

	@Column(name = "url")
	private String url;

	@Column(name = "html_url")
	private String htmlURL;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO, termVector = TermVector.YES)
	@Column(name = "description", length=10000)
	private String description;

	@Column(name = "homepage_url")
	private String homepageURL;

	@Column(name = "download_url")
	private String downloadURL;

	@Column(name = "url_name")
	private String urlName;

	@Column(name = "medium_logo_url")
	private String mediumLogoURL;

	@Column(name = "small_logo_url")
	private String smallLogoURL;

	@Field(name = "user_count", index=Index.YES, analyze=Analyze.YES, store=Store.NO, termVector = TermVector.YES)
	@Column(name = "user_count")
	private Long userCount;

	@Column(name = "average_rating")
	private Double averageRating;

	
	@Field(name = "rating_count", index=Index.YES, analyze=Analyze.YES, store=Store.NO, termVector = TermVector.YES)
	@Column(name = "rating_count")
	private Long ratingCount;

	
	@Field(name = "review_count", index=Index.YES, analyze=Analyze.YES, store=Store.NO, termVector = TermVector.YES)
	@Column(name = "review_count")
	private Long reviewCount;

	@Column(name = "analysis_id", insertable = false, updatable = false)
	private Long analysisId;

	@ManyToOne(cascade=CascadeType.ALL, targetEntity=OpenHubAnalysisEntity.class)
	@JoinColumn(name = "analysis_id", referencedColumnName = "id")
	@IndexedEmbedded
	private OpenHubAnalysisEntity analysis;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="project_license",
			joinColumns=@JoinColumn(name = "project_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name = "license_id", referencedColumnName="id"),
			uniqueConstraints=@UniqueConstraint(columnNames={"project_id","license_id"})
			)
	@IndexedEmbedded
	private List<OpenHubLicenseEntity> licenses;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="project_tag",
			joinColumns=@JoinColumn(name = "project_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name = "tag_id", referencedColumnName="id"),
			uniqueConstraints=@UniqueConstraint(columnNames={"project_id","tag_id"})
			)
	@IndexedEmbedded
	private List<OpenHubTagEntity> tags;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="project")	
	@IndexedEmbedded
	private List<OpenHubLinkEntity> links;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "project_activity_index_id", referencedColumnName = "id")
	private OpenHubProjectActivityIndexEntity projectActivityIndex;
	
	public OpenHubProjectEntity() {
		this.links = new LinkedList<OpenHubLinkEntity>();
		this.tags = new LinkedList<OpenHubTagEntity>();
		this.licenses = new LinkedList<OpenHubLicenseEntity>();
	}

	public List<OpenHubLinkEntity> getLinks() {
		return links;
	}
	
	public void setLinks(List<OpenHubLinkEntity> links) {
		this.links = links;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
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

	public Long getUserCount() {
		return userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public Long getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Long ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Long getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Long analysisId) {
		this.analysisId = analysisId;
	}

	public OpenHubAnalysisEntity getAnalysis() {
		return analysis;
	}
	
	public void setAnalysis(OpenHubAnalysisEntity analysis) {
		this.analysis = analysis;
	}
	
	public List<OpenHubLicenseEntity> getLicenses() {
		return licenses;
	}
	
	public void setLicenses(List<OpenHubLicenseEntity> licenses) {
		this.licenses = licenses;
	}
	
	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		this.reviewCount = reviewCount;
	}

	public List<OpenHubTagEntity> getTags() {
		return tags;
	}
	
	public void setTags(List<OpenHubTagEntity> tags) {
		this.tags = tags;
	}
		
	public OpenHubProjectActivityIndexEntity getProjectActivityIndex() {
		return projectActivityIndex;
	}

	public void setProjectActivityIndex(
			OpenHubProjectActivityIndexEntity projectActivityIndex) {
		this.projectActivityIndex = projectActivityIndex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpenHubProjectEntity other = (OpenHubProjectEntity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
