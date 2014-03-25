package br.ufba.dcc.mestrado.computacao.ohloh.data.language;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohLanguageResult {
	
	@XStreamImplicit(itemFieldName="language")
	private List<OhLohLanguageDTO> languages;
	
	public List<OhLohLanguageDTO> getLanguages() {
		return languages;
	}
	
	public void setLanguages(List<OhLohLanguageDTO> languages) {
		this.languages = languages;
	}
	
}
