package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.sizefact.OhLohSizeFactEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.sizefact.OhLohSizeFactDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohSizeFactRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohSizeFactRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohSizeFactService;

@Service(OhLohSizeFactServiceImpl.BEAN_NAME)
public class OhLohSizeFactServiceImpl 
		extends DefaultOhLohServiceImpl<OhLohSizeFactDTO, Long, OhLohSizeFactEntity> 
		implements OhLohSizeFactService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "ohLohSizeFactService";
	
	@Autowired
	public OhLohSizeFactServiceImpl(@Qualifier(OhLohSizeFactRepositoryImpl.BEAN_NAME) OhLohSizeFactRepository repository) {
		super(repository, OhLohSizeFactDTO.class, OhLohSizeFactEntity.class);
	}

	@Override
	public Long countAllByProject(OhLohProjectEntity project) {
		return ((OhLohSizeFactRepository) getRepository()).countAllByProject(project);
	}

	@Override
	public List<OhLohSizeFactEntity> findByProject(OhLohProjectEntity project) {
		return ((OhLohSizeFactRepository) getRepository()).findByProject(project);
	}
	
	

}
