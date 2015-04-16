package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.language.OpenHubLanguageEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.language.OpenHubLanguageDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.LanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.LanguageRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubLanguageService;

@Service(OpenHubLanguageServiceImpl.BEAN_NAME)
public class OpenHubLanguageServiceImpl extends DefaultOpenHubServiceImpl<OpenHubLanguageDTO, Long, OpenHubLanguageEntity>
		implements OpenHubLanguageService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789509366943106982L;
	
	public static final String BEAN_NAME =  "ohLohLanguageService";
	

	@Autowired
	public OpenHubLanguageServiceImpl(@Qualifier(LanguageRepositoryImpl.BEAN_NAME) LanguageRepository repository) {
		super(repository, OpenHubLanguageDTO.class, OpenHubLanguageEntity.class);
	}

}
