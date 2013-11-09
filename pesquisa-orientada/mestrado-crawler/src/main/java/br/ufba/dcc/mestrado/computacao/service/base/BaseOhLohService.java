package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;

public interface BaseOhLohService<DTO extends OhLohResultDTO, ID extends Number, E extends BaseEntity<ID>> extends Serializable{

	public E process(DTO dto) throws Exception;
	
	public E buildEntity(DTO dto) throws Exception;
	
	public void validateEntity(E entity) throws Exception;

	public abstract E saveEntity(E entity) throws Exception;
}
