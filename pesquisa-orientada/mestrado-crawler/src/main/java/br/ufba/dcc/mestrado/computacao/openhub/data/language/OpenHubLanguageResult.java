package br.ufba.dcc.mestrado.computacao.openhub.data.language;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = OpenHubLanguageResult.NODE_NAME)
public class OpenHubLanguageResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8793594382885788487L;
	
	public final static String NODE_NAME = "result";
	
	
	private List<OpenHubLanguageDTO> languages;
	
	public OpenHubLanguageResult() {
		super();
	}
	
	@XmlElement(name = "language")
	public List<OpenHubLanguageDTO> getLanguages() {
		return languages;
	}
	
	public void setLanguages(List<OpenHubLanguageDTO> languages) {
		this.languages = languages;
	}
	
}
