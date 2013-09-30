package br.ufba.dcc.mestrado.computacao.ohloh.data.language;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohLanguageResult {
	
	@XStreamImplicit(itemFieldName="language")
	private List<OhLohLanguageDTO> ohLohLanguages;
	
	public List<OhLohLanguageDTO> getOhLohLanguages() {
		return ohLohLanguages;
	}
	
	public void setOhLohLanguages(List<OhLohLanguageDTO> ohLohLanguages) {
		this.ohLohLanguages = ohLohLanguages;
	}
	
	
}
