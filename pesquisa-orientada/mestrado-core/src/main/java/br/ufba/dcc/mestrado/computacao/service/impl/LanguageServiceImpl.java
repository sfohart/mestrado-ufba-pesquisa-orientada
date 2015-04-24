package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.language.OpenHubLanguageEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.LanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.LanguageRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.LanguageService;

@Service(LanguageServiceImpl.BEAN_NAME)
public class LanguageServiceImpl extends BaseServiceImpl<Long, OpenHubLanguageEntity>
		implements LanguageService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789509366943106982L;
	
	public static final String BEAN_NAME =  "languageService";
	

	@Autowired
	public LanguageServiceImpl(@Qualifier(LanguageRepositoryImpl.BEAN_NAME) LanguageRepository repository) {
		super(repository, OpenHubLanguageEntity.class);
	}

}
