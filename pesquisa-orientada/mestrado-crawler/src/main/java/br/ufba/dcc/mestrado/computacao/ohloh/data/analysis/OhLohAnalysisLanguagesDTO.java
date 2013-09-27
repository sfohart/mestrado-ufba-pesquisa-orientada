package br.ufba.dcc.mestrado.computacao.ohloh.data.analysis;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("languages")
public class OhLohAnalysisLanguagesDTO implements OhLohResultDTO {

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
	private List<OhLohAnalysisLanguageDTO> content;

	public String getGraphURL() {
		return graphURL;
	}

	public void setGraphURL(String graphURL) {
		this.graphURL = graphURL;
	}

	public List<OhLohAnalysisLanguageDTO> getContent() {
		return content;
	}

	public void setContent(List<OhLohAnalysisLanguageDTO> content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
