package br.ufba.dcc.mestrado.computacao.openhub.data.enlistment;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubBaseResult;

@XmlRootElement(name = OpenHubBaseResult.NODE_NAME)
public class OpenHubEnlistmentResult implements OpenHubBaseResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8927582575999294187L;

	private List<OpenHubEnlistmentDTO> enlistments;
	
	@XmlElement(name = "enlistment")
	public List<OpenHubEnlistmentDTO> getEnlistments() {
		return enlistments;
	}
	
	public void setEnlistments(List<OpenHubEnlistmentDTO> enlistments) {
		this.enlistments = enlistments;
	}
	
}
