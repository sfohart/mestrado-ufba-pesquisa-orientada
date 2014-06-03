package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.sizefact.OhLohSizeFactEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.sizefact.OhLohSizeFactDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.SizeFactRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.SizeFactRepositoryImpl;
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
	public OhLohSizeFactServiceImpl(@Qualifier(SizeFactRepositoryImpl.BEAN_NAME) SizeFactRepository repository) {
		super(repository, OhLohSizeFactDTO.class, OhLohSizeFactEntity.class);
	}

	@Override
	public Long countAllByProject(OhLohProjectEntity project) {
		return ((SizeFactRepository) getRepository()).countAllByProject(project);
	}

	@Override
	public List<OhLohSizeFactEntity> findByProject(OhLohProjectEntity project) {
		return ((SizeFactRepository) getRepository()).findByProject(project);
	}
	
	

}
