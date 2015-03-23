package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.sizefact.OpenHubSizeFactEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.sizefact.OpenHubSizeFactDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.SizeFactRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.SizeFactRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubSizeFactService;

@Service(OpenHubSizeFactServiceImpl.BEAN_NAME)
public class OpenHubSizeFactServiceImpl 
		extends DefaultOpenHubServiceImpl<OpenHubSizeFactDTO, Long, OpenHubSizeFactEntity> 
		implements OpenHubSizeFactService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "ohLohSizeFactService";
	
	@Autowired
	public OpenHubSizeFactServiceImpl(@Qualifier(SizeFactRepositoryImpl.BEAN_NAME) SizeFactRepository repository) {
		super(repository, OpenHubSizeFactDTO.class, OpenHubSizeFactEntity.class);
	}

	@Override
	public Long countAllByProject(OpenHubProjectEntity project) {
		return ((SizeFactRepository) getRepository()).countAllByProject(project);
	}

	@Override
	public List<OpenHubSizeFactEntity> findByProject(OpenHubProjectEntity project) {
		return ((SizeFactRepository) getRepository()).findByProject(project);
	}
	
	

}
