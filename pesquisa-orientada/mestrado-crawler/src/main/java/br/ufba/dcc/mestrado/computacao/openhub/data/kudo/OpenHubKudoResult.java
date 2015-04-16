package br.ufba.dcc.mestrado.computacao.openhub.data.kudo;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OpenHubKudoResult {

	
	@XStreamImplicit(itemFieldName="kudo")
	private List<OpenHubKudoDTO> kudos;
	
	public List<OpenHubKudoDTO> getKudos() {
		return kudos;
	}
	
	public void setKudos(List<OpenHubKudoDTO> kudos) {
		this.kudos = kudos;
	}

}
