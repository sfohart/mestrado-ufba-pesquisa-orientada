package br.ufba.dcc.mestrado.computacao.openhub.data.organization;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubPortifolioProjectDTO.NODE_NAME)
public class OpenHubPortifolioProjectDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6203597426768385191L;

	public final static String NODE_NAME = "project";

	private String name;
	private String activity;
	private String primaryLanguage;
	private Long iUseThis;
	private Double communityRating;

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "activity")
	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	@XmlElement(name = "primary_language")
	public String getPrimaryLanguage() {
		return primaryLanguage;
	}

	public void setPrimaryLanguage(String primaryLanguage) {
		this.primaryLanguage = primaryLanguage;
	}

	@XmlElement(name = "i_use_this")
	public Long getiUseThis() {
		return iUseThis;
	}

	public void setiUseThis(Long iUseThis) {
		this.iUseThis = iUseThis;
	}

	@XmlElement(name = "community_rating")
	public Double getCommunityRating() {
		return communityRating;
	}

	public void setCommunityRating(Double communityRating) {
		this.communityRating = communityRating;
	}

}
