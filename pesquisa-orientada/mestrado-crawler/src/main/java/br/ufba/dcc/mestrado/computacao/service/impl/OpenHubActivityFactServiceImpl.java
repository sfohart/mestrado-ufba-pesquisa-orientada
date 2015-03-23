package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.activityfact.OpenHubActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.activityfact.OpenHubActivityFactDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.ActivityFactRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.ActivityFactRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubActivityFactService;

@Service(OpenHubActivityFactServiceImpl.BEAN_NAME)
public class OpenHubActivityFactServiceImpl 
		extends DefaultOpenHubServiceImpl<OpenHubActivityFactDTO, Long, OpenHubActivityFactEntity> 
		implements OpenHubActivityFactService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "ohLohActivityFactService";
	
	@Autowired
	public OpenHubActivityFactServiceImpl(@Qualifier(ActivityFactRepositoryImpl.BEAN_NAME) ActivityFactRepository repository) {
		super(repository, OpenHubActivityFactDTO.class, OpenHubActivityFactEntity.class);
	}

	@Override
	public Long countAllByProject(OpenHubProjectEntity project) {
		return ((ActivityFactRepository) getRepository()).countAllByProject(project);
	}

	@Override
	public List<OpenHubActivityFactEntity> findByProject(OpenHubProjectEntity project) {
		return ((ActivityFactRepository) getRepository()).findByProject(project);
	}
	
	

}
