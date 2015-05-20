package br.ufba.dcc.mestrado.computacao.openhub.data.analysis;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;


@XmlRootElement(name = OpenHubAnalysisLanguagesDTO.NODE_NAME)
public class OpenHubAnalysisLanguagesDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3047964132725598415L;
	
	public final static String NODE_NAME = "languages";
	
	private String id;

	private String graphURL;

	
	private List<OpenHubAnalysisLanguageDTO> content;
	
	private String color;

	@XmlAttribute(name = "graph_url")
	public String getGraphURL() {
		return graphURL;
	}

	public void setGraphURL(String graphURL) {
		this.graphURL = graphURL;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@XmlElement(name = "language")
	public List<OpenHubAnalysisLanguageDTO> getContent() {
		return content;
	}

	public void setContent(List<OpenHubAnalysisLanguageDTO> content) {
		this.content = content;
	}

	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
