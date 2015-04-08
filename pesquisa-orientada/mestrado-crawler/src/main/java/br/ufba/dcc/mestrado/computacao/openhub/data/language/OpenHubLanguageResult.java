package br.ufba.dcc.mestrado.computacao.openhub.data.language;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OpenHubLanguageResult {
	
	@XStreamImplicit(itemFieldName="language")
	private List<OpenHubLanguageDTO> languages;
	
	public List<OpenHubLanguageDTO> getLanguages() {
		return languages;
	}
	
	public void setLanguages(List<OpenHubLanguageDTO> languages) {
		this.languages = languages;
	}
	
}
