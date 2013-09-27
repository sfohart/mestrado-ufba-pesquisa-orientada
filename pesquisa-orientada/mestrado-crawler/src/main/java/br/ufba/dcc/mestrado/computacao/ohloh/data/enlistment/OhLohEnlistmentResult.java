package br.ufba.dcc.mestrado.computacao.ohloh.data.enlistment;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohEnlistmentResult {
	
	@XStreamImplicit(itemFieldName="enlistment")
	private List<OhLohEnlistmentDTO> ohLohEnlistments;
	
	public List<OhLohEnlistmentDTO> getOhLohEnlistments() {
		return ohLohEnlistments;
	}
	
	public void setOhLohEnlistments(List<OhLohEnlistmentDTO> ohLohEnlistments) {
		this.ohLohEnlistments = ohLohEnlistments;
	}
	
	
}
