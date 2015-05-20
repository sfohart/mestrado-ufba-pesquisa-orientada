package br.ufba.dcc.mestrado.computacao.openhub.data.analysis;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = OpenHubAnalysisResult.NODE_NAME)
public class OpenHubAnalysisResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7775842708109166229L;

	public final static String NODE_NAME = "result";

	private OpenHubAnalysisDTO analysis;
	
	@XmlElement(name = "analysis")
	public OpenHubAnalysisDTO getAnalysis() {
		return analysis;
	}
	
	public void setAnalysis(OpenHubAnalysisDTO analysis) {
		this.analysis = analysis;
	}
	
}
