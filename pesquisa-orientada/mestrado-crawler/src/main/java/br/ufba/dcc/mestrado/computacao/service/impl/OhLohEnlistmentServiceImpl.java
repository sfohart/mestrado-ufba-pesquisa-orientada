package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.enlistment.OhLohEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.enlistment.OhLohEnlistmentDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohEnlistmentRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohEnlistmentService;

@Service(OhLohEnlistmentServiceImpl.BEAN_NAME)
public class OhLohEnlistmentServiceImpl extends DefaultOhLohServiceImpl<OhLohEnlistmentDTO, Long, OhLohEnlistmentEntity> implements OhLohEnlistmentService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009205814992395490L;
	
	public static final String BEAN_NAME =  "ohLohEnlistmentService";
	
	@Autowired
	public OhLohEnlistmentServiceImpl(@Qualifier("ohLohEnlistmentRepository") OhLohEnlistmentRepository repository) {
		super(repository, OhLohEnlistmentDTO.class, OhLohEnlistmentEntity.class);
	}

	public List<OhLohEnlistmentEntity> findByProject(OhLohProjectEntity project) {
		return ((OhLohEnlistmentRepository) getRepository()).findByProject(project);
	}
	
	
	public Long countAllByProject(OhLohProjectEntity project) {
		return ((OhLohEnlistmentRepository) getRepository()).countAllByProject(project);
	}
}
