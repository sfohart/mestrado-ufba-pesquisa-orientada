package br.ufba.dcc.mestrado.computacao.ohloh.data.analysis;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias(OhLohAnalysisLanguageDTO.NODE_NAME)
public class OhLohAnalysisLanguageDTO implements OhLohResultDTO {

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

	private String content;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

}
