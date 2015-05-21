package br.ufba.dcc.mestrado.computacao.openhub.data.account;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.kudoskore.OpenHubKudoScoreDTO;

@XmlRootElement(name = OpenHubAccountDTO.NODE_NAME)
public class OpenHubAccountDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4161579546763839506L;

	public final static String NODE_NAME = "account";
	
	private String id;
		
	/**
	 * The public name for this Account.
	 */
	private String name;
	
	private String about;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private String homepageURL;
	
	private String avatarURL;
	
	private String emailSHA1;
	
	private Long postsCount;
	
	private String location;
	
	private String countryCode;
	
	private Double latitude;
	
	private Double longitude;
	
	private OpenHubKudoScoreDTO kudoScore;
	
	private String url;
	
	private String htmlURL;
	
	private String login;
	
	private String twitterAccount;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "about")
	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
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

	@XmlElement(name = "homepage_url")
	public String getHomepageURL() {
		return homepageURL;
	}

	public void setHomepageURL(String homepageURL) {
		this.homepageURL = homepageURL;
	}

	@XmlElement(name = "avatar_url")
	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}

	@XmlElement(name = "email_sha1")
	public String getEmailSHA1() {
		return emailSHA1;
	}

	public void setEmailSHA1(String emailSHA1) {
		this.emailSHA1 = emailSHA1;
	}

	@XmlElement(name = "posts_count")
	public Long getPostsCount() {
		return postsCount;
	}

	public void setPostsCount(Long postsCount) {
		this.postsCount = postsCount;
	}

	@XmlElement(name = "location")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@XmlElement(name = "country_code")
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@XmlElement(name = "latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@XmlElement(name = "longitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@XmlElement(name = "kudo_score")
	public OpenHubKudoScoreDTO getKudoScore() {
		return kudoScore;
	}
	
	public void setKudoScore(OpenHubKudoScoreDTO kudoScore) {
		this.kudoScore = kudoScore;
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

	@XmlElement(name = "login")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@XmlElement(name = "twitter_account")
	public String getTwitterAccount() {
		return twitterAccount;
	}

	public void setTwitterAccount(String twitterAccount) {
		this.twitterAccount = twitterAccount;
	}
	
	
}
