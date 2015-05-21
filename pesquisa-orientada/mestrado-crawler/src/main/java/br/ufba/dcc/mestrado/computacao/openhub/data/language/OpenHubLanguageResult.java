package br.ufba.dcc.mestrado.computacao.openhub.data.language;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubBaseResult;

@XmlRootElement(name = OpenHubBaseResult.NODE_NAME)
public class OpenHubLanguageResult implements OpenHubBaseResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8793594382885788487L;
	
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
