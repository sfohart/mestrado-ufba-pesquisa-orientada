package br.ufba.dcc.mestrado.computacao.ohloh.data.kudo;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohKudoResult {

	
	@XStreamImplicit(itemFieldName="kudo")
	private List<OhLohKudoDTO> kudos;
	
	public List<OhLohKudoDTO> getKudos() {
		return kudos;
	}
	
	public void setKudos(List<OhLohKudoDTO> kudos) {
		this.kudos = kudos;
	}

}
