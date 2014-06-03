package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohTagEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohTagDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.TagRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.TagRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohTagService;

@Service(OhLohTagServiceImpl.BEAN_NAME)
public class OhLohTagServiceImpl extends DefaultOhLohServiceImpl<OhLohTagDTO, Long, OhLohTagEntity>
		implements OhLohTagService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789509366943106982L;
	
	public static final String BEAN_NAME =  "ohLohTagService";
	

	@Autowired
	public OhLohTagServiceImpl(@Qualifier(TagRepositoryImpl.BEAN_NAME) TagRepository repository) {
		super(repository, OhLohTagDTO.class, OhLohTagEntity.class);
	}


	@Override
	public List<OhLohTagEntity> findTagListByName(String name) {
		return ((TagRepository) getRepository()).findTagListByName(name);
	}
	
	@Override
	public OhLohTagEntity findByName(String name) {
		return ((TagRepository) getRepository()).findByName(name);
	}

}
