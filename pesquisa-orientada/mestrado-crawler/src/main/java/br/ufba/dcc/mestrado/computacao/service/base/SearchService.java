package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.search.SearchRequest;
import br.ufba.dcc.mestrado.computacao.search.SearchResponse;

public interface SearchService extends Serializable {

	SearchResponse findAllProjects(SearchRequest searchRequest);

	List<OhLohProjectEntity> findRelatedProjects(OhLohProjectEntity project, int maxResults) throws IOException;
	
}
