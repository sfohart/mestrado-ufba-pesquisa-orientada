package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.activityfact.OhLohActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.activityfact.OhLohActivityFactDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohActivityFactRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohActivityFactRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohActivityFactService;

@Service(OhLohActivityFactServiceImpl.BEAN_NAME)
public class OhLohActivityFactServiceImpl 
		extends DefaultOhLohServiceImpl<OhLohActivityFactDTO, Long, OhLohActivityFactEntity> 
		implements OhLohActivityFactService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "ohLohActivityFactService";
	
	@Autowired
	public OhLohActivityFactServiceImpl(@Qualifier(OhLohActivityFactRepositoryImpl.BEAN_NAME) OhLohActivityFactRepository repository) {
		super(repository, OhLohActivityFactDTO.class, OhLohActivityFactEntity.class);
	}

	@Override
	public Long countAllByProject(OhLohProjectEntity project) {
		return ((OhLohActivityFactRepository) getRepository()).countAllByProject(project);
	}

	@Override
	public List<OhLohActivityFactEntity> findByProject(OhLohProjectEntity project) {
		return ((OhLohActivityFactRepository) getRepository()).findByProject(project);
	}
	
	

}
