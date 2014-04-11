package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.concurrent.ExecutionException;

public interface IndexBuilderService {
	
	void buildIndex() throws InterruptedException, ExecutionException;

}
