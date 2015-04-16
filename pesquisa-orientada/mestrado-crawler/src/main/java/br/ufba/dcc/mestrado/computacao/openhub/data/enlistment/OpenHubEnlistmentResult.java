package br.ufba.dcc.mestrado.computacao.openhub.data.enlistment;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OpenHubEnlistmentResult {
	
	@XStreamImplicit(itemFieldName="enlistment")
	private List<OpenHubEnlistmentDTO> enlistments;
	
	public List<OpenHubEnlistmentDTO> getEnlistments() {
		return enlistments;
	}
	
	public void setEnlistments(List<OpenHubEnlistmentDTO> enlistments) {
		this.enlistments = enlistments;
	}
	
}
