package br.ufba.dcc.mestrado.computacao.entities;

import java.io.Serializable;

public interface BaseEntity<ID extends Serializable> extends Serializable {

	ID getId();
	
	void setId(ID id);
	
}
