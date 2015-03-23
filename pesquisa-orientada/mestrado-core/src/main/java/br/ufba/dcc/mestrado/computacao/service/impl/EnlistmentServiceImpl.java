package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.enlistment.OpenHubEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.EnlistmentRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.EnlistmentRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.EnlistmentService;

@Service(EnlistmentServiceImpl.BEAN_NAME)
public class EnlistmentServiceImpl extends BaseServiceImpl<Long, OpenHubEnlistmentEntity> implements EnlistmentService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "enlistmentService";
	
	@Autowired
	public EnlistmentServiceImpl(@Qualifier(EnlistmentRepositoryImpl.BEAN_NAME) EnlistmentRepository repository) {
		super(repository, OpenHubEnlistmentEntity.class);
	}

	public List<OpenHubEnlistmentEntity> findByProject(OpenHubProjectEntity project) {
		return ((EnlistmentRepository) getRepository()).findByProject(project);
	}
	
	
	public Long countAllByProject(OpenHubProjectEntity project) {
		return ((EnlistmentRepository) getRepository()).countAllByProject(project);
	}
}
