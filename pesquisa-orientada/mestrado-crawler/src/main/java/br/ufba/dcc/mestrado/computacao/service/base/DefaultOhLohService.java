package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;

public interface DefaultOhLohService<DTO extends OhLohResultDTO, ID extends Number, E extends BaseEntity<ID>> 
		extends BaseOhLohService<ID, E>, Serializable {

	E process(DTO dto) throws Exception;
	
	E buildEntity(DTO dto) throws Exception;
	
	void validateEntity(E entity) throws Exception;
	
}
