package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohTagDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohTagRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohTagService;

@Service(OhLohTagServiceImpl.BEAN_NAME)
public class OhLohTagServiceImpl extends DefaultOhLohServiceImpl<OhLohTagDTO, Long, OhLohTagEntity>
		implements OhLohTagService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789509366943106982L;
	
	public static final String BEAN_NAME =  "ohLohTagService";
	

	@Autowired
	public OhLohTagServiceImpl(@Qualifier("ohLohTagRepository") OhLohTagRepository repository) {
		super(repository, OhLohTagDTO.class, OhLohTagEntity.class);
	}

}
