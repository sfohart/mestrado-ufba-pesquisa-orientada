package br.ufba.dcc.mestrado.computacao.entities;

import java.io.Serializable;

public interface BaseEntity<ID extends Number> extends Serializable {

	ID getId();
	
	void setId(ID id);
	
}
