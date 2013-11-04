package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;

import br.ufba.dcc.mestrado.computacao.service.impl.SearchServiceImpl.SearchResult;

public interface SearchService extends Serializable {

	SearchResult findAllProjects(String query);
	
}
