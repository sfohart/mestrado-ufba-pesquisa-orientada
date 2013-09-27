package br.ufba.dcc.mestrado.computacao.ohloh.data.kudo;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohKudoResult {

	
	@XStreamImplicit(itemFieldName="kudo")
	private List<OhLohKudoDTO> ohLohKudos;
	
	public List<OhLohKudoDTO> getOhLohKudos() {
		return ohLohKudos;
	}
	
	public void setOhLohKudos(List<OhLohKudoDTO> ohLohKudos) {
		this.ohLohKudos = ohLohKudos;
	}
	
	
}
