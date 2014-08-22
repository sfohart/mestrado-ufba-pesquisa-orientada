package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.LinkRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.LinkRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.LinkService;

@Service(LinkServiceImpl.BEAN_NAME)
public class LinkServiceImpl extends BaseServiceImpl<Long, OhLohLinkEntity> implements LinkService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "linkService";
	
	@Autowired
	public LinkServiceImpl(@Qualifier(LinkRepositoryImpl.BEAN_NAME) LinkRepository repository) {
		super(repository, OhLohLinkEntity.class);
	}
	
	public List<OhLohLinkEntity> findByProject(OhLohProjectEntity project) {
		return ((LinkRepository) getRepository()).findByProject(project);
	}
	
	
	public Long countAllByProject(OhLohProjectEntity project) {
		return ((LinkRepository) getRepository()).countAllByProject(project);
	}

}
