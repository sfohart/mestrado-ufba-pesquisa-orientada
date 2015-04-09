
package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

public interface DefaultOpenHubService<DTO extends OpenHubResultDTO, ID extends Number, E extends BaseEntity<ID>> 
		extends BaseOpenHubService<ID, E>, Serializable {

	E process(DTO dto) throws Exception;
	
	E buildEntity(DTO dto) throws Exception;
	
	void validateEntity(E entity) throws Exception;
	
}

