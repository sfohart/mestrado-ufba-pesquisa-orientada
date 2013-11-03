package br.ufba.dcc.mestrado.computacao.entities.ohloh.project;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisEntity;

@Entity
@Table(name = OhLohProjectEntity.NODE_NAME)
@Indexed(index = OhLohProjectEntity.NODE_NAME) 
public class OhLohProjectEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9128774489845120800L;

	public final static String NODE_NAME = "project";

	@Id
	private Long id;
	
	@Column(name = "name")
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String name;

	@Column(name = "url")
	private String url;

	@Column(name = "html_url")
	private String htmlURL;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "description", length = 10000)
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

	@Column(name = "user_count")
	private Long userCount;

	@Column(name = "average_rating")
	private Double averageRating;

	@Column(name = "rating_count")
	private Long ratingCount;

	@Column(name = "review_count")
	private Long reviewCount;

	@Column(name = "analysis_id", insertable = false, updatable = false)
	private Long analysisId;

	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=OhLohAnalysisEntity.class)
	@JoinColumn(name = "analysis_id", referencedColumnName = "id")
	@IndexedEmbedded
	private OhLohAnalysisEntity ohLohAnalysis;

	@ManyToMany(cascade=CascadeType.ALL)
	@IndexedEmbedded
	private List<OhLohLicenseEntity> ohLohLicenses;

	@ManyToMany(cascade=CascadeType.ALL)
	@IndexedEmbedded
	private List<OhLohTagEntity> ohLohTags;
	
	@OneToMany(cascade=CascadeType.ALL)
	@IndexedEmbedded
	private List<OhLohLinkEntity> ohLohLinks;

	public List<OhLohLinkEntity> getOhLohLinks() {
		return ohLohLinks;
	}

	public void setOhLohLinks(List<OhLohLinkEntity> ohLohLinks) {
		this.ohLohLinks = ohLohLinks;
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

	public OhLohAnalysisEntity getOhLohAnalysis() {
		return ohLohAnalysis;
	}

	public void setOhLohAnalysis(OhLohAnalysisEntity ohLohAnalysis) {
		this.ohLohAnalysis = ohLohAnalysis;
	}

	public List<OhLohLicenseEntity> getOhLohLicenses() {
		return ohLohLicenses;
	}

	public void setOhLohLicenses(List<OhLohLicenseEntity> ohLohLicenses) {
		this.ohLohLicenses = ohLohLicenses;
	}

	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		this.reviewCount = reviewCount;
	}

	public List<OhLohTagEntity> getOhLohTags() {
		return ohLohTags;
	}

	public void setOhLohTags(List<OhLohTagEntity> ohLohTags) {
		this.ohLohTags = ohLohTags;
	}

}
