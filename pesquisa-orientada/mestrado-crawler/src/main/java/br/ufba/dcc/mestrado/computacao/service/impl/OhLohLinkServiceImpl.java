package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohLinkDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohLinkRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLinkService;

@Service(OhLohLinkServiceImpl.BEAN_NAME)
public class OhLohLinkServiceImpl extends DefaultOhLohServiceImpl<OhLohLinkDTO, Long, OhLohLinkEntity> implements OhLohLinkService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "ohLohLinkService";
	
	@Autowired
	public OhLohLinkServiceImpl(@Qualifier("ohLohLinkRepository") OhLohLinkRepository repository) {
		super(repository, OhLohLinkDTO.class, OhLohLinkEntity.class);
	}
	
	public List<OhLohLinkEntity> findByProject(OhLohProjectEntity project) {
		return ((OhLohLinkRepository) getRepository()).findByProject(project);
	}
	
	
	public Long countAllByProject(OhLohProjectEntity project) {
		return ((OhLohLinkRepository) getRepository()).countAllByProject(project);
	}

}
