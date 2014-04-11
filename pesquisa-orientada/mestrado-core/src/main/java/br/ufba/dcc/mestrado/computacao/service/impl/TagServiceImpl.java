package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.TagRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.TagRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.TagService;

@Service(TagServiceImpl.BEAN_NAME)
public class TagServiceImpl extends BaseServiceImpl<Long, OhLohTagEntity>
		implements TagService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789509366943106982L;
	
	public static final String BEAN_NAME =  "tagService";
	

	@Autowired
	public TagServiceImpl(@Qualifier(TagRepositoryImpl.BEAN_NAME) TagRepository repository) {
		super(repository, OhLohTagEntity.class);
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
