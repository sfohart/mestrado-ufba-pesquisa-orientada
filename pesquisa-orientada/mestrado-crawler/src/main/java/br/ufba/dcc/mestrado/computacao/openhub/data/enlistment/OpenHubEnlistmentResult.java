package br.ufba.dcc.mestrado.computacao.openhub.data.enlistment;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = OpenHubEnlistmentResult.NODE_NAME)
public class OpenHubEnlistmentResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8927582575999294187L;


	public final static String NODE_NAME = "result";
	
	
	private List<OpenHubEnlistmentDTO> enlistments;
	
	@XmlElement(name = "enlistment")
	public List<OpenHubEnlistmentDTO> getEnlistments() {
		return enlistments;
	}
	
	public void setEnlistments(List<OpenHubEnlistmentDTO> enlistments) {
		this.enlistments = enlistments;
	}
	
}
