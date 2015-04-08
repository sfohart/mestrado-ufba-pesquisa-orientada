package br.ufba.dcc.mestrado.computacao.openhub.data.factoid;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias(OpenHubFactoidDTO.NODE_NAME)
@XStreamConverter(value = ToAttributedValueConverter.class, strings = { "description" })
public class OpenHubFactoidDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2218492589477862288L;

	public final static String NODE_NAME = "factoid";
	
	@XStreamAsAttribute
	private String type;

	@XStreamAlias("analysis_id")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long analysisId;

	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long severity;

	private String description;
	


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSeverity() {
		return severity;
	}

	public void setSeverity(Long severity) {
		this.severity = severity;
	}

	public Long getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Long analysisId) {
		this.analysisId = analysisId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
