package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubLinkDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.LinkRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.LinkRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubLinkService;

@Service(OpenHubLinkServiceImpl.BEAN_NAME)
public class OpenHubLinkServiceImpl extends DefaultOpenHubServiceImpl<OpenHubLinkDTO, Long, OpenHubLinkEntity> implements OpenHubLinkService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "ohLohLinkService";
	
	@Autowired
	public OpenHubLinkServiceImpl(@Qualifier(LinkRepositoryImpl.BEAN_NAME) LinkRepository repository) {
		super(repository, OpenHubLinkDTO.class, OpenHubLinkEntity.class);
	}
	
	public List<OpenHubLinkEntity> findByProject(OpenHubProjectEntity project) {
		return ((LinkRepository) getRepository()).findByProject(project);
	}
	
	
	public Long countAllByProject(OpenHubProjectEntity project) {
		return ((LinkRepository) getRepository()).countAllByProject(project);
	}

}
