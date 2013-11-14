package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohLinkDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohLinkRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLinkService;

@Service(OhLohLinkServiceImpl.BEAN_NAME)
public class OhLohLinkServiceImpl extends BaseOhLohServiceImpl<OhLohLinkDTO, Long, OhLohLinkEntity> implements OhLohLinkService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "ohLohLinkService";
	
	@Autowired
	public OhLohLinkServiceImpl(@Qualifier("ohLohLinkRepository") OhLohLinkRepository repository) {
		super(repository, OhLohLinkDTO.class, OhLohLinkEntity.class);
	}

}
