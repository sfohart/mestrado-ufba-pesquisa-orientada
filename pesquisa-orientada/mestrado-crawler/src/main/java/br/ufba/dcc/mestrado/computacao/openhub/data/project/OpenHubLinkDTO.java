package br.ufba.dcc.mestrado.computacao.openhub.data.project;

import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubLinkDTO.NODE_NAME)
public class OpenHubLinkDTO implements OpenHubResultDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1944222135871973315L;

	public final static String NODE_NAME = "link";
	
	private String id;
	
	private String category;
	
	private String title;
	
	private String url;

	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
