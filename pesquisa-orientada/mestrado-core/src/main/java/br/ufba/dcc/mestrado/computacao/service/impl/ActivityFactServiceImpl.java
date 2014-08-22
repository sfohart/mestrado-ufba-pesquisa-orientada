package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.activityfact.OhLohActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.ActivityFactRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.ActivityFactRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.ActivityFactService;

@Service(ActivityFactServiceImpl.BEAN_NAME)
public class ActivityFactServiceImpl 
		extends BaseServiceImpl<Long, OhLohActivityFactEntity> 
		implements ActivityFactService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "activityFactService";
	
	@Autowired
	public ActivityFactServiceImpl(@Qualifier(ActivityFactRepositoryImpl.BEAN_NAME) ActivityFactRepository repository) {
		super(repository, OhLohActivityFactEntity.class);
	}

	@Override
	public Long countAllByProject(OhLohProjectEntity project) {
		return ((ActivityFactRepository) getRepository()).countAllByProject(project);
	}

	@Override
	public List<OhLohActivityFactEntity> findByProject(OhLohProjectEntity project) {
		return ((ActivityFactRepository) getRepository()).findByProject(project);
	}
	
	

}
