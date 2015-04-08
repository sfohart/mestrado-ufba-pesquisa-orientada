
package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubTagDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.TagRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.TagRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubTagService;

@Service(OpenHubTagServiceImpl.BEAN_NAME)
public class OpenHubTagServiceImpl extends DefaultOpenHubServiceImpl<OpenHubTagDTO, Long, OpenHubTagEntity>
		implements OpenHubTagService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789509366943106982L;
	
	public static final String BEAN_NAME =  "ohLohTagService";
	

	@Autowired
	public OpenHubTagServiceImpl(@Qualifier(TagRepositoryImpl.BEAN_NAME) TagRepository repository) {
		super(repository, OpenHubTagDTO.class, OpenHubTagEntity.class);
	}


	@Override
	public List<OpenHubTagEntity> findTagListByName(String name) {
		return ((TagRepository) getRepository()).findTagListByName(name);
	}
	
	@Override
	public OpenHubTagEntity findByName(String name) {
		return ((TagRepository) getRepository()).findByName(name);
	}

}

