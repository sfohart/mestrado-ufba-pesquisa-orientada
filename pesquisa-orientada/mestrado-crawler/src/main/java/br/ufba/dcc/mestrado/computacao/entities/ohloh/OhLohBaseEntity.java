package br.ufba.dcc.mestrado.computacao.entities.ohloh;

import java.io.Serializable;

public interface OhLohBaseEntity<ID extends Number> extends Serializable {

	ID getId();
	
	void setId(ID id);
	
}
