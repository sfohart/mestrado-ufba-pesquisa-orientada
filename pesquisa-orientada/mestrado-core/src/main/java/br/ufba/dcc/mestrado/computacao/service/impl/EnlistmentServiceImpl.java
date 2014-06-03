package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.enlistment.OhLohEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.EnlistmentRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.EnlistmentRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.EnlistmentService;

@Service(EnlistmentServiceImpl.BEAN_NAME)
public class EnlistmentServiceImpl extends BaseServiceImpl<Long, OhLohEnlistmentEntity> implements EnlistmentService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "enlistmentService";
	
	@Autowired
	public EnlistmentServiceImpl(@Qualifier(EnlistmentRepositoryImpl.BEAN_NAME) EnlistmentRepository repository) {
		super(repository, OhLohEnlistmentEntity.class);
	}

	public List<OhLohEnlistmentEntity> findByProject(OhLohProjectEntity project) {
		return ((EnlistmentRepository) getRepository()).findByProject(project);
	}
	
	
	public Long countAllByProject(OhLohProjectEntity project) {
		return ((EnlistmentRepository) getRepository()).countAllByProject(project);
	}
}
