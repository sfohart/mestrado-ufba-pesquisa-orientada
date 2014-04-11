package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.sizefact.OhLohSizeFactEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.SizeFactRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.SizeFactRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.SizeFactService;

@Service(SizeFactServiceImpl.BEAN_NAME)
public class SizeFactServiceImpl 
		extends BaseServiceImpl<Long, OhLohSizeFactEntity> 
		implements SizeFactService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "sizeFactService";
	
	@Autowired
	public SizeFactServiceImpl(@Qualifier(SizeFactRepositoryImpl.BEAN_NAME) SizeFactRepository repository) {
		super(repository, OhLohSizeFactEntity.class);
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
