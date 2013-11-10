package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;

import br.ufba.dcc.mestrado.computacao.search.SearchRequest;
import br.ufba.dcc.mestrado.computacao.search.SearchResponse;

public interface SearchService extends Serializable {

	SearchResponse findAllProjects(SearchRequest searchRequest);
	
}
