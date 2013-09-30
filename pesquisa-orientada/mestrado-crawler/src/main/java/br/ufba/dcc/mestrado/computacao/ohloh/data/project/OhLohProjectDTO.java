package br.ufba.dcc.mestrado.computacao.ohloh.data.project;

import java.sql.Timestamp;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.analysis.OhLohAnalysisDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableDoubleXStreamConverter;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.converters.extended.ISO8601SqlTimestampConverter;

@XStreamAlias(OhLohProjectDTO.NODE_NAME)
public class OhLohProjectDTO implements OhLohResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9128774489845120800L;

	public final static String NODE_NAME = "project";

	@XStreamAsAttribute
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long id;

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

	@XStreamAlias("download_url")
	private String downloadURL;

	@XStreamAlias("url_name")
	private String urlName;

	@XStreamAlias("medium_logo_url")
	private String mediumLogoURL;

	@XStreamAlias("small_logo_url")
	private String smallLogoURL;

	@XStreamAlias("user_count")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long userCount;

	@XStreamAlias("average_rating")
	@XStreamConverter(value = NullableDoubleXStreamConverter.class)
	private Double averageRating;

	@XStreamAlias("rating_count")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long ratingCount;

	@XStreamAlias("review_count")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long reviewCount;

	@XStreamAlias("analysis_id")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long analysisId;

	@XStreamAlias("analysis")
	private OhLohAnalysisDTO ohLohAnalysis;

	@XStreamAlias("licenses")
	private List<OhLohLicenseDTO> ohLohLicenses;

	@XStreamAlias("tags")	
	private List<OhLohTagDTO> ohLohTags;

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

	public List<OhLohLicenseDTO> getOhLohLicenses() {
		return ohLohLicenses;
	}

	public void setOhLohLicenses(List<OhLohLicenseDTO> ohLohLicenses) {
		this.ohLohLicenses = ohLohLicenses;
	}

	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		this.reviewCount = reviewCount;
	}

	public List<OhLohTagDTO> getOhLohTags() {
		return ohLohTags;
	}

	public void setOhLohTags(List<OhLohTagDTO> ohLohTags) {
		this.ohLohTags = ohLohTags;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OhLohAnalysisDTO getOhLohAnalysis() {
		return ohLohAnalysis;
	}

	public void setOhLohAnalysis(OhLohAnalysisDTO ohLohAnalysis) {
		this.ohLohAnalysis = ohLohAnalysis;
	}

}
