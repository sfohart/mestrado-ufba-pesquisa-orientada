package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectActivityIndexEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.ProjectActivityIndexRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.ProjectActivityIndexRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectActivityIndexService;

@Service(ProjectActivityIndexServiceImpl.BEAN_NAME)
public class ProjectActivityIndexServiceImpl
		extends BaseServiceImpl<Long, OhLohProjectActivityIndexEntity>
		implements ProjectActivityIndexService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1924406564885541029L;
	
	public static final String BEAN_NAME =  "projectActivityIndexService";

	@Autowired
	public ProjectActivityIndexServiceImpl(@Qualifier(ProjectActivityIndexRepositoryImpl.BEAN_NAME) ProjectActivityIndexRepository repository) {
		super(repository, OhLohProjectActivityIndexEntity.class);
	}
	
	@Override
	public OhLohProjectActivityIndexEntity findByValue(Long name) {
		return ((ProjectActivityIndexRepository) getRepository()).findByValue(name);
	}
	
}
