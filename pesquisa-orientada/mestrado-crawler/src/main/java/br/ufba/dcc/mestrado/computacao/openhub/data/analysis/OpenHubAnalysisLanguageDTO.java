package br.ufba.dcc.mestrado.computacao.openhub.data.analysis;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias(OpenHubAnalysisLanguageDTO.NODE_NAME)
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"entryContent"})
public class OpenHubAnalysisLanguageDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1672599783346624942L;

	public final static String NODE_NAME = "language";

	@XStreamAlias("id")
	@XStreamAsAttribute
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long languageId;

	@XStreamAsAttribute
	private String percentage;

	@XStreamAsAttribute
	private String color;

	private String entryContent;

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getEntryContent() {
		return entryContent;
	}

	public void setEntryContent(String entryContent) {
		this.entryContent = entryContent;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

}
