package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.enlistment.OpenHubEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.enlistment.OpenHubEnlistmentDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.EnlistmentRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.EnlistmentRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubEnlistmentService;

@Service(OpenHubEnlistmentServiceImpl.BEAN_NAME)
public class OpenHubEnlistmentServiceImpl extends DefaultOpenHubServiceImpl<OpenHubEnlistmentDTO, Long, OpenHubEnlistmentEntity> implements OpenHubEnlistmentService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "ohLohEnlistmentService";
	
	@Autowired
	public OpenHubEnlistmentServiceImpl(@Qualifier(EnlistmentRepositoryImpl.BEAN_NAME) EnlistmentRepository repository) {
		super(repository, OpenHubEnlistmentDTO.class, OpenHubEnlistmentEntity.class);
	}

	public List<OpenHubEnlistmentEntity> findByProject(OpenHubProjectEntity project) {
		return ((EnlistmentRepository) getRepository()).findByProject(project);
	}
	
	
	public Long countAllByProject(OpenHubProjectEntity project) {
		return ((EnlistmentRepository) getRepository()).countAllByProject(project);
	}
}
