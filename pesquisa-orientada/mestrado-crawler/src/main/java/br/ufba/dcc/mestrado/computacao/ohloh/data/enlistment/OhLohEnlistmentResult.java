package br.ufba.dcc.mestrado.computacao.ohloh.data.enlistment;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohEnlistmentResult {
	
	@XStreamImplicit(itemFieldName="enlistment")
	private List<OhLohEnlistmentDTO> enlistments;
	
	public List<OhLohEnlistmentDTO> getEnlistments() {
		return enlistments;
	}
	
	public void setEnlistments(List<OhLohEnlistmentDTO> enlistments) {
		this.enlistments = enlistments;
	}
	
}
