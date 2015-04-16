package br.ufba.dcc.mestrado.computacao.openhub.data.analysis;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("languages")
public class OpenHubAnalysisLanguagesDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3047964132725598415L;
	
	@XStreamAsAttribute	
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long id;

	@XStreamAlias("graph_url")
	@XStreamAsAttribute
	private String graphURL;

	@XStreamImplicit(itemFieldName = "language")
	private List<OpenHubAnalysisLanguageDTO> content;
	
	@XStreamAsAttribute
	private String color;

	public String getGraphURL() {
		return graphURL;
	}

	public void setGraphURL(String graphURL) {
		this.graphURL = graphURL;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<OpenHubAnalysisLanguageDTO> getContent() {
		return content;
	}

	public void setContent(List<OpenHubAnalysisLanguageDTO> content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
