package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.search.SearchRequest;
import br.ufba.dcc.mestrado.computacao.search.SearchResponse;

public interface SearchService extends Serializable {

	SearchResponse findAllProjects(SearchRequest searchRequest);

	List<OpenHubProjectEntity> findRelatedProjects(
			OpenHubProjectEntity project,
			Integer firstResult,
			Integer maxResults) throws IOException;
	
}
